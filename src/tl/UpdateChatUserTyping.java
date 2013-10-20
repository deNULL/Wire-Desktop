package tl;

import java.nio.ByteBuffer;

public class UpdateChatUserTyping extends tl.TUpdate {
  
  public UpdateChatUserTyping(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    user_id = buffer.getInt();
  }
  
  public UpdateChatUserTyping(int chat_id, int user_id) {
    this.chat_id = chat_id;
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3c46cfe6);
    }
    buffer.putInt(chat_id);
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(UpdateChatUserTyping chat_id:" + chat_id + " user_id:" + user_id + ")";
  }
}
