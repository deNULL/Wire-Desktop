package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteChatUser extends tl.TLFunction {
  public int chat_id;
  public tl.TInputUser user_id;
  
  public DeleteChatUser(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    user_id = (tl.TInputUser) TL.read(buffer);
  }
  
  public DeleteChatUser(int chat_id, tl.TInputUser user_id) {
    this.chat_id = chat_id;
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc3c5cd23);
    }
    buffer.putInt(chat_id);
    user_id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + user_id.length();
  }
  
  public String toString() {
    return "(DeleteChatUser chat_id:" + chat_id + " user_id:" + user_id + ")";
  }
}
