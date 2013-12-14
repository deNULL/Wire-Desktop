package ru.denull.mtproto;

import ru.denull.wire.stub.tl.TLObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ru.denull.wire.lib.CryptoUtils.*;

public class EncryptedMessage extends Message {
    public long auth_key_id;
    public long server_salt;
    public long session_id;
    public int seq_no;
    public boolean significant;

    public EncryptedMessage(long message_id, TLObject payload, long session_id, long auth_key_id, long server_salt, int seq_no, boolean significant) {
        super(message_id, payload);
        this.auth_key_id = auth_key_id;
        this.server_salt = server_salt;
        this.session_id = session_id;
        this.seq_no = seq_no;
        this.significant = significant;
    }

    public EncryptedMessage(long message_id, TLObject payload, long session_id, long auth_key_id, long server_salt, int seq_no) {
        super(message_id, payload);
        this.auth_key_id = auth_key_id;
        this.server_salt = server_salt;
        this.session_id = session_id;
        this.seq_no = seq_no;
    }

    public ByteBuffer encrypt(byte[] auth_key) throws Exception {
        //System.out.println("sending " + payload);
        if (error != 0) {
            return ByteBuffer.allocateDirect(4).putInt(-error);
        }

        if (auth_key == null) {
            throw new Exception("auth_key missing!");
        }

        int innerSize = payload.length(true);

        int size = innerSize + 32;
        while ((size % 16) != 0) size++; // TODO: fix

        ByteBuffer buffer = ByteBuffer.allocateDirect(size + 24);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(auth_key_id);

        ByteBuffer innerBuffer = ByteBuffer.allocateDirect(size);
        innerBuffer.order(ByteOrder.LITTLE_ENDIAN);
        innerBuffer.putLong(server_salt);

        innerBuffer.putLong(session_id);
        innerBuffer.putLong(message_id);
        innerBuffer.putInt(seq_no * 2 + (significant ? 1 : 0));
        innerBuffer.putInt(innerSize);

        payload.writeTo(innerBuffer);

        byte[] msg_key = substr(SHA1(innerBuffer, 0, innerSize + 32), 4, 16);
        buffer.put(msg_key);

        int x = 0;
        byte[] sha1_a = SHA1(concat(msg_key, substr(auth_key, x, 32)));
        byte[] sha1_b = SHA1(concat(substr(auth_key, 32 + x, 16), msg_key, substr(auth_key, 48 + x, 16)));
        byte[] sha1_c = SHA1(concat(substr(auth_key, 64 + x, 32), msg_key));
        byte[] sha1_d = SHA1(concat(msg_key, substr(auth_key, 96 + x, 32)));

        byte[] aes_key = concat(substr(sha1_a, 0, 8), substr(sha1_b, 8, 12), substr(sha1_c, 4, 12));
        byte[] aes_iv = concat(substr(sha1_a, 8, 12), substr(sha1_b, 0, 8), substr(sha1_c, 16, 4), substr(sha1_d, 0, 8));

        buffer.put(AESEncrypt(innerBuffer, 0, size, aes_key, aes_iv));

        buffer.rewind();
        return buffer;
    }

}
