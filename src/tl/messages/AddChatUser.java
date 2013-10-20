package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class AddChatUser extends tl.TLFunction {
  public int chat_id;
  public tl.TInputUser user_id;
  public int fwd_limit;
  
  public AddChatUser(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    user_id = (tl.TInputUser) TL.read(buffer);
    fwd_limit = buffer.getInt();
  }
  
  public AddChatUser(int chat_id, tl.TInputUser user_id, int fwd_limit) {
    this.chat_id = chat_id;
    this.user_id = user_id;
    this.fwd_limit = fwd_limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2ee9ee9e);
    }
    buffer.putInt(chat_id);
    user_id.writeTo(buffer, false);
    buffer.putInt(fwd_limit);
  	return buffer;
  }
  
  public int length() {
    return 12 + user_id.length();
  }
  
  public String toString() {
    return "(AddChatUser chat_id:" + chat_id + " user_id:" + user_id + " fwd_limit:" + fwd_limit + ")";
  }
}
