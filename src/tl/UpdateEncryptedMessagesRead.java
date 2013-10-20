package tl;

import java.nio.ByteBuffer;

public class UpdateEncryptedMessagesRead extends tl.TUpdate {

  
  public UpdateEncryptedMessagesRead(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    max_date = buffer.getInt();
    date = buffer.getInt();
  }
  
  public UpdateEncryptedMessagesRead(int chat_id, int max_date, int date) {
    this.chat_id = chat_id;
    this.max_date = max_date;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x38fe25b7);
    }
    buffer.putInt(chat_id);
    buffer.putInt(max_date);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateEncryptedMessagesRead: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(updateEncryptedMessagesRead chat_id:" + chat_id + " max_date:" + max_date + " date:" + date + ")";
  }
}
