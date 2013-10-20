package tl;

import java.nio.ByteBuffer;

public class EncryptedChatRequested extends tl.TEncryptedChat {
  
  public EncryptedChatRequested(ByteBuffer buffer) {
    id = buffer.getInt();
    access_hash = buffer.getLong();
    date = buffer.getInt();
    admin_id = buffer.getInt();
    participant_id = buffer.getInt();
    g_a = TL.readString(buffer);
    nonce = TL.readString(buffer);
  }
  
  public EncryptedChatRequested(int id, long access_hash, int date, int admin_id, int participant_id, byte[] g_a, byte[] nonce) {
    this.id = id;
    this.access_hash = access_hash;
    this.date = date;
    this.admin_id = admin_id;
    this.participant_id = participant_id;
    this.g_a = g_a;
    this.nonce = nonce;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xfda9a7b7);
    }
    buffer.putInt(id);
    buffer.putLong(access_hash);
    buffer.putInt(date);
    buffer.putInt(admin_id);
    buffer.putInt(participant_id);
    TL.writeString(buffer, g_a, false);
    TL.writeString(buffer, nonce, false);
  	return buffer;
  }
  
  public int length() {
    return 24 + TL.length(g_a) + TL.length(nonce);
  }
  
  public String toString() {
    return "(EncryptedChatRequested id:" + id + " access_hash:" + String.format("0x%016x", access_hash) + " date:" + date + " admin_id:" + admin_id + " participant_id:" + participant_id + " g_a:" + TL.toString(g_a) + " nonce:" + TL.toString(nonce) + ")";
  }
}
