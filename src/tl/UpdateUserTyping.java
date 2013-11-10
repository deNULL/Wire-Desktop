package tl;

import java.nio.ByteBuffer;

public class UpdateUserTyping extends tl.TUpdate {

  
  public UpdateUserTyping(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
  }
  
  public UpdateUserTyping(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6baa8508);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateUserTyping: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(updateUserTyping user_id:" + user_id + ")";
  }
}
