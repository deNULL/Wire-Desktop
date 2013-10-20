package tl;

import java.nio.ByteBuffer;

public class MessageActionChatAddUser extends tl.TMessageAction {
  
  public MessageActionChatAddUser(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public MessageActionChatAddUser(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5e3cfc4b);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(MessageActionChatAddUser user_id:" + user_id + ")";
  }
}
