package tl;

import java.nio.ByteBuffer;

public class UpdateEncryptedChatTyping extends tl.TUpdate {
  
  public UpdateEncryptedChatTyping(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public UpdateEncryptedChatTyping(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1710f156);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UpdateEncryptedChatTyping chat_id:" + chat_id + ")";
  }
}
