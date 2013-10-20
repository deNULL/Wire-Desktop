package tl;

import java.nio.ByteBuffer;

public class MessageActionChatDeleteUser extends tl.TMessageAction {
  
  public MessageActionChatDeleteUser(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public MessageActionChatDeleteUser(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb2ae9b0c);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(MessageActionChatDeleteUser user_id:" + user_id + ")";
  }
}
