package tl;

import java.nio.ByteBuffer;

public class EncryptedChat extends tl.TEncryptedChat {

  
  public EncryptedChat(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    access_hash = buffer.getLong();
    date = buffer.getInt();
    admin_id = buffer.getInt();
    participant_id = buffer.getInt();
    g_a_or_b = TL.readString(buffer);
    nonce = TL.readString(buffer);
    key_fingerprint = buffer.getLong();
  }
  
  public EncryptedChat(int id, long access_hash, int date, int admin_id, int participant_id, byte[] g_a_or_b, byte[] nonce, long key_fingerprint) {
    this.id = id;
    this.access_hash = access_hash;
    this.date = date;
    this.admin_id = admin_id;
    this.participant_id = participant_id;
    this.g_a_or_b = g_a_or_b;
    this.nonce = nonce;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6601d14f);
    }
    buffer.putInt(id);
    buffer.putLong(access_hash);
    buffer.putInt(date);
    buffer.putInt(admin_id);
    buffer.putInt(participant_id);
    TL.writeString(buffer, g_a_or_b, false);
    TL.writeString(buffer, nonce, false);
    buffer.putLong(key_fingerprint);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 32 + TL.length(g_a_or_b) + TL.length(nonce);
  }
  
  public String toString() {
    return "(encryptedChat id:" + id + " access_hash:" + String.format("0x%016x", access_hash) + " date:" + date + " admin_id:" + admin_id + " participant_id:" + participant_id + " g_a_or_b:" + TL.toString(g_a_or_b) + " nonce:" + TL.toString(nonce) + " key_fingerprint:" + String.format("0x%016x", key_fingerprint) + ")";
  }
}
