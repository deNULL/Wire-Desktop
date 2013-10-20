package tl;

import java.nio.ByteBuffer;

public class UpdateUserTyping extends tl.TUpdate {
  
  public UpdateUserTyping(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public UpdateUserTyping(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6baa8508);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UpdateUserTyping user_id:" + user_id + ")";
  }
}
