package tl;

import java.nio.ByteBuffer;

public class InputUserContact extends tl.TInputUser {
  
  public InputUserContact(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public InputUserContact(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x86e94f65);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(InputUserContact user_id:" + user_id + ")";
  }
}
