package tl;

import java.nio.ByteBuffer;

public class EncryptedChatWaiting extends tl.TEncryptedChat {

  
  public EncryptedChatWaiting(ByteBuffer buffer) {
    id = buffer.getInt();
    access_hash = buffer.getLong();
    date = buffer.getInt();
    admin_id = buffer.getInt();
    participant_id = buffer.getInt();
  }
  
  public EncryptedChatWaiting(int id, long access_hash, int date, int admin_id, int participant_id) {
    this.id = id;
    this.access_hash = access_hash;
    this.date = date;
    this.admin_id = admin_id;
    this.participant_id = participant_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3bf703dc);
    }
    buffer.putInt(id);
    buffer.putLong(access_hash);
    buffer.putInt(date);
    buffer.putInt(admin_id);
    buffer.putInt(participant_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedChatWaiting: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(encryptedChatWaiting id:" + id + " access_hash:" + String.format("0x%016x", access_hash) + " date:" + date + " admin_id:" + admin_id + " participant_id:" + participant_id + ")";
  }
}
