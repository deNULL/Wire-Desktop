package ru.denull.mtproto;

import ru.denull.wire.lib.Base64;
import ru.denull.wire.lib.Log;
import ru.denull.wire.stub.tl.TL;
import ru.denull.wire.stub.tl.TLObject;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.prefs.Preferences;

import static ru.denull.wire.lib.CryptoUtils.*;

public class Auth {


    private static final String TAG = "Auth";
    public Server server;
    public byte[] auth_key;
    public long auth_key_id;
    public long server_salt;
    public boolean authorized;
    public Preferences pref;
    public KeyStore keyStore;

    public AuthState state = AuthState.NONE;
    private long retry_id;
    private ArrayList<AuthCallback> callbacks = new ArrayList<AuthCallback>();
    private boolean importAuthorization;

    private byte[] nonce, server_nonce, new_nonce;
    private byte[] tmp_aes_key, tmp_aes_iv;
    private long g;
    private BigInteger g_a, b, dh_prime;
    private Random rand = new Random();

    public enum AuthState {NONE, REQUESTED_PQ, REQUESTED_DH, SENT_DH, FAILED, COMPLETE}

    ;

    public interface AuthCallback {
        public void done(Server server, byte[] auth_key);

        public void error();
    }

    public Auth(Server server) {
        this.server = server;
    }

    public Auth(Server server, Preferences pref) {
        this.server = server;
        this.pref = pref;
        /*try {
      this.keyStore = KeyStore.getInstance("KeychainStore", "Apple");
    } catch (Exception e) {
      try {
        this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }*/
        System.out.println("auth key = " + (pref.get("auth_key", "").length() > 0 ? "true" : "false") + ", authorisation = " + pref.getBoolean("authorized", false));

        this.auth_key = Base64.decode(pref.get("auth_key", ""));
        this.auth_key_id = pref.getLong("auth_key_id", 0);
        this.server_salt = pref.getLong("server_salt", 0);
        this.authorized = pref.getBoolean("authorized", false);
        this.state = (this.auth_key_id != 0) ? AuthState.COMPLETE : AuthState.NONE;
    }

    public Auth(Server server, byte[] auth_key, long auth_key_id, long server_salt) {
        this.server = server;
        this.auth_key = auth_key;
        this.auth_key_id = auth_key_id;
        this.server_salt = server_salt;
        this.state = (this.auth_key_id != 0) ? AuthState.COMPLETE : AuthState.NONE;
    }

    public void logged() {
        authorized = true;
        if (pref != null) {
            pref.putBoolean("authorized", true);
        }
    }

    public void loggedOut() {
        authorized = false;
        if (pref != null) {
            pref.putBoolean("authorized", false);
        }
    }

    private void reset(boolean full) {
        if (full) {
            auth_key = null;
            auth_key_id = 0;
            server_salt = 0;
        }

        retry_id = 0;
        nonce = null;
        server_nonce = null;
        new_nonce = null;
        tmp_aes_key = null;
        tmp_aes_iv = null;
        g = 0;
        g_a = null;
        b = null;
        dh_prime = null;
    }

    private void fail() {
        reset(true);
        state = AuthState.FAILED;
        ArrayList<AuthCallback> cb = callbacks;
        callbacks = new ArrayList<Auth.AuthCallback>();

        for (AuthCallback callback : cb) {
            callback.error();
        }
    }

    private void complete() {
        reset(false);
        state = AuthState.COMPLETE;
        if (pref != null) {
            pref.put("auth_key", Base64.encodeToString(auth_key, Base64.NO_WRAP));
            pref.putLong("auth_key_id", auth_key_id);
            pref.putLong("server_salt", server_salt);
            pref.putBoolean("authorized", false);
        }
        if (server.old_session_id != 0) {
            server.call(new DestroySession(server.old_session_id), new Server.RPCCallback<TLObject>() {

                public void done(TLObject result) {
                    Log.i(TAG, "Successfully destroyed old session");
                }

                public void error(int code, String message) {
                    Log.i(TAG, "Unable to destroy old session");
                }

            }, false);
            server.old_session_id = 0;
        }
        try {
            server.sendQueued();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<AuthCallback> cb = callbacks;
        callbacks = new ArrayList<Auth.AuthCallback>();

        for (AuthCallback callback : cb) {
            callback.done(server, auth_key);
        }
    }

    public boolean generateKey(boolean forceRegenerate, final AuthCallback callback) {
        return generateKey(forceRegenerate, callback, false);
    }

    public boolean generateKey(boolean forceRegenerate, final AuthCallback callback, boolean importAutorization) {
        if (callback != null) {
            callbacks.add(callback);
        }

        if (state != AuthState.NONE && state != AuthState.FAILED && state != AuthState.COMPLETE) {
            return false; // already in progress
        }

        this.importAuthorization = importAutorization;

        if (state == AuthState.COMPLETE && !forceRegenerate) {
            complete();
            return true;
        }

        nonce = new byte[16];
        rand.nextBytes(nonce);

        retry_id = 0;

        state = AuthState.REQUESTED_PQ;
        try {
            server.send(new ReqPq(nonce), false);
        } catch (Exception e) {
            Log.e(TAG, "Failed to request PQ");
            e.printStackTrace();
            fail();
            return false;
        }
        return true;
    }

    public void generateKey(TLObject reply) throws Exception {
        switch (state) {
            case REQUESTED_PQ: {
                if (!(reply instanceof ResPQ)) { // unexpected response
                    fail();
                    return;
                }

                ResPQ response = (ResPQ) reply;

                if (!Arrays.equals(nonce, response.nonce)) {
                    fail();
                    return;
                }

                server_nonce = response.server_nonce;

                BigInteger p = factor(response.pq);
                BigInteger q = response.pq.divide(p);
                if (p.compareTo(q) > 0) { // swap
                    BigInteger tmp = p;
                    p = q;
                    q = tmp;
                }
                new_nonce = new byte[32];
                rand.nextBytes(new_nonce);

                PQInnerData innerData = new PQInnerData(response.pq, p, q, nonce, server_nonce, new_nonce);
                ByteBuffer buffer = ByteBuffer.allocateDirect(innerData.length(true));
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                innerData.writeTo(buffer);
                buffer.rewind();

                byte[] hash = SHA1(buffer, 0, buffer.capacity());
                byte[] data_with_hash = new byte[255];
                System.arraycopy(hash, 0, data_with_hash, 0, hash.length);
                buffer.get(data_with_hash, hash.length, buffer.capacity());

                byte[] encrypted_data = RSAEncrypt(data_with_hash,
                        new BigInteger("0C150023E2F70DB7985DED064759CFECF0AF328E69A41DAF4D6F01B538135A6F91F8F8B2A0EC9BA9720CE352EFCF6C5680FFC424BD634864902DE0B4BD6D49F4E580230E3AE97D95C8B19442B3C0A10D8F5633FECEDD6926A7F6DAB0DDB7D457F9EA81B8465FCD6FFFEED114011DF91C059CAEDAF97625F6C96ECC74725556934EF781D866B34F011FCE4D835A090196E9A5F0E4449AF7EB697DDB9076494CA5F81104A305B6DD27665722C46B60E5DF680FB16B210607EF217652E60236C255F6A28315F4083A96791D7214BF64C1DF4FD0DB1944FB26A2A57031B32EEE64AD15A8BA68885CDE74A5BFC920F6ABF59BA5C75506373E7130F9042DA922179251F", 16),
                        new BigInteger("010001", 16));

                server.send(new ReqDHParams(nonce, server_nonce, p, q, response.server_public_key_fingerprints[0], encrypted_data), false);
                state = AuthState.REQUESTED_DH;
                break;
            }

            case REQUESTED_DH: {
                if (!(reply instanceof ServerDHParamsOk)) { // => ResponseServerDHFail
                    fail();
                    return;
                }

                ServerDHParamsOk response = (ServerDHParamsOk) reply;

                if (!Arrays.equals(nonce, response.nonce)) {
                    fail();
                    return;
                }

                if (!Arrays.equals(server_nonce, response.server_nonce)) {
                    fail();
                    return;
                }

                byte[] sha_ns = SHA1(concat(new_nonce, server_nonce));
                byte[] sha_sn = SHA1(concat(server_nonce, new_nonce));
                byte[] sha_nn = SHA1(concat(new_nonce, new_nonce));

                tmp_aes_key = concat(sha_ns, substr(sha_sn, 0, 12));
                tmp_aes_iv = concat(substr(sha_sn, 12, 8), sha_nn, substr(new_nonce, 0, 4));

                byte[] answer = AESDecrypt(response.encrypted_answer, 0, response.encrypted_answer.length, tmp_aes_key, tmp_aes_iv);

                //byte[] sha1_answer = Arrays.copyOfRange(answer, 0, 20);

                ByteBuffer answer_buf = ByteBuffer.wrap(Arrays.copyOfRange(answer, 20, answer.length));
                answer_buf.order(ByteOrder.LITTLE_ENDIAN);

                ServerDHInnerData serverInnerData = (ServerDHInnerData) TL.read(answer_buf);

                if (!Arrays.equals(nonce, serverInnerData.nonce)) {
                    fail();
                    return;
                }

                if (!Arrays.equals(server_nonce, serverInnerData.server_nonce)) {
                    fail();
                    return;
                }

                g = serverInnerData.g;
                g_a = serverInnerData.g_a;
                dh_prime = serverInnerData.dh_prime;
                server.time_diff = (int) (serverInnerData.server_time - (System.currentTimeMillis() / 1000));

                requestClientDH();
                state = AuthState.SENT_DH;
                break;
            }

            case SENT_DH: {
                if (!(reply instanceof TSetClientDHParamsAnswer)) {
                    fail();
                    return;
                }

                TSetClientDHParamsAnswer response = (TSetClientDHParamsAnswer) reply;

                auth_key = new byte[256];
                byte[] buffer = g_a.modPow(b, dh_prime).toByteArray();
                System.arraycopy(buffer, Math.max(buffer.length - 256, 0), auth_key, 256 - Math.min(buffer.length, 256), Math.min(buffer.length, 256));

                ByteBuffer tmp = ByteBuffer.allocateDirect(8);
                tmp.order(ByteOrder.LITTLE_ENDIAN);
                for (int i = 0; i < 8; i++) {
                    tmp.put((byte) (new_nonce[i] ^ server_nonce[i]));
                }
                server_salt = tmp.getLong(0);

                tmp = ByteBuffer.wrap(SHA1(auth_key));
                tmp.order(ByteOrder.LITTLE_ENDIAN);
                auth_key_id = tmp.getLong(12);

                long auth_key_aux_hash = tmp.getLong(0);
                tmp = ByteBuffer.allocateDirect(new_nonce.length + 9);
                tmp.order(ByteOrder.LITTLE_ENDIAN);
                tmp.put(new_nonce);
                if (reply instanceof DhGenOk) {
                    tmp.put((byte) 0x01);
                } else if (reply instanceof DhGenFail) {
                    tmp.put((byte) 0x02);
                } else {
                    tmp.put((byte) 0x03);
                }
                tmp.putLong(auth_key_aux_hash);
                byte[] new_nonce_hash1 = substr(SHA1(tmp, 0, tmp.capacity()), 4, 16);

                if (reply instanceof DhGenRetry) {
                    // retry
                    retry_id = auth_key_aux_hash;

                    requestClientDH();
                    state = AuthState.SENT_DH;
                    return;
                }

                if (reply instanceof DhGenFail) {
                    fail();
                    return;
                }

                if (!Arrays.equals(new_nonce_hash1, ((DhGenOk) response).new_nonce_hash1)) {
                    fail();
                    return;
                }

                debug("auth_key: " + (new BigInteger(buffer)), "0x" + (new BigInteger(buffer)).toString(16));
                debug("server_salt: 0x" + Long.toHexString(server_salt));
                debug("auth_key_id: 0x" + Long.toHexString(auth_key_id));
                complete();
                break;
            }
        }
    }

    private void requestClientDH() throws Exception {
        byte[] bytes = new byte[255];
        rand.nextBytes(bytes);
        b = new BigInteger(bytes);
        BigInteger g_b = BigInteger.valueOf(g).modPow(b, dh_prime);

        ClientDHInnerData clientInnerData = new ClientDHInnerData(nonce, server_nonce, retry_id, g_b);
        ByteBuffer buffer = ByteBuffer.allocateDirect(clientInnerData.length(true));
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        clientInnerData.writeTo(buffer);
        buffer.rewind();

        byte[] hash = SHA1(buffer, 0, buffer.capacity());

        int len = hash.length + buffer.capacity();
        while (len % 16 != 0) len++;

        byte[] data_with_hash = new byte[len];
        System.arraycopy(hash, 0, data_with_hash, 0, hash.length);
        buffer.get(data_with_hash, hash.length, buffer.capacity());
        byte[] encrypted_data = AESEncrypt(data_with_hash, 0, len, tmp_aes_key, tmp_aes_iv);

        server.send(new SetClientDHParams(nonce, server_nonce, encrypted_data), false);
    }
}
