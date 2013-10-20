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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb2ae9b0c);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatDeleteUser: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(messageActionChatDeleteUser user_id:" + user_id + ")";
  }
}
