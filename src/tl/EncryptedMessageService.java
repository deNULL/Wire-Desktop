package tl;

import java.nio.ByteBuffer;

public class EncryptedMessageService extends tl.TEncryptedMessage {

  
  public EncryptedMessageService(ByteBuffer buffer) throws Exception {
    random_id = buffer.getLong();
    chat_id = buffer.getInt();
    date = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public EncryptedMessageService(long random_id, int chat_id, int date, byte[] bytes) {
    this.random_id = random_id;
    this.chat_id = chat_id;
    this.date = date;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x23734b06);
    }
    buffer.putLong(random_id);
    buffer.putInt(chat_id);
    buffer.putInt(date);
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedMessageService: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(bytes);
  }
  
  public String toString() {
    return "(encryptedMessageService random_id:" + String.format("0x%016x", random_id) + " chat_id:" + chat_id + " date:" + date + " bytes:" + TL.toString(bytes) + ")";
  }
}
