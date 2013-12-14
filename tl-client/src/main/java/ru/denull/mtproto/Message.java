package ru.denull.mtproto;

import ru.denull.wire.stub.tl.TL;
import ru.denull.wire.stub.tl.TLObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ru.denull.wire.lib.CryptoUtils.*;

public class Message {
    public int error = 0;

    public long message_id;
    public TLObject payload;

    public Message(long message_id, TLObject payload) {
        this.message_id = message_id;
        this.payload = payload;
    }

    public Message(int error) {
        this.error = error;
    }

    public ByteBuffer encrypt(byte[] auth_key) throws Exception {
        //System.out.println("sending raw " + payload);
        if (error != 0) {
            return ByteBuffer.allocateDirect(4).putInt(-error);
        }

        int size = payload.length(true);
        ByteBuffer buffer = ByteBuffer.allocateDirect(size + 20);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putLong(0);
        buffer.putLong(message_id);
        buffer.putInt(size);

        payload.writeTo(buffer);

        buffer.rewind();
        return buffer;
    }

    public static Message decrypt(ByteBuffer buffer) throws Exception {
        return decrypt(buffer, null, 0);
    }

    public static Message decrypt(ByteBuffer buffer, byte[] auth_key, long auth_key_id) throws Exception {
        if (buffer == null) {
            return null;
        }

        if (buffer.capacity() == 4) {
            return new Message(-buffer.getInt());
        }

        long _auth_key_id = buffer.getLong();

        if (_auth_key_id == 0) {
            long message_id = buffer.getLong();
            int size = buffer.getInt();

            return new Message(message_id, TL.read(buffer));
        } else if (_auth_key_id == auth_key_id) {
            byte[] msg_key = new byte[16];
            buffer.get(msg_key);

            byte[] encrypted_data = new byte[buffer.capacity() - 24];
            buffer.get(encrypted_data);

            int x = 8;
            byte[] sha1_a = SHA1(concat(msg_key, substr(auth_key, x, 32)));
            byte[] sha1_b = SHA1(concat(substr(auth_key, 32 + x, 16), msg_key, substr(auth_key, 48 + x, 16)));
            byte[] sha1_c = SHA1(concat(substr(auth_key, 64 + x, 32), msg_key));
            byte[] sha1_d = SHA1(concat(msg_key, substr(auth_key, 96 + x, 32)));

            byte[] aes_key = concat(substr(sha1_a, 0, 8), substr(sha1_b, 8, 12), substr(sha1_c, 4, 12));
            byte[] aes_iv = concat(substr(sha1_a, 8, 12), substr(sha1_b, 0, 8), substr(sha1_c, 16, 4), substr(sha1_d, 0, 8));

            ByteBuffer innerBuffer = ByteBuffer.wrap(AESDecrypt(encrypted_data, 0, encrypted_data.length, aes_key, aes_iv));
            innerBuffer.order(ByteOrder.LITTLE_ENDIAN);
            long server_salt = innerBuffer.getLong();
            long session_id = innerBuffer.getLong();
            long message_id = innerBuffer.getLong();
            int seq_no = innerBuffer.getInt();
            int size = innerBuffer.getInt();

            return new EncryptedMessage(message_id, TL.read(innerBuffer), session_id, _auth_key_id, server_salt, seq_no);
        } else {
            throw new Exception("Unknown auth_key received");
        }
    }

}
