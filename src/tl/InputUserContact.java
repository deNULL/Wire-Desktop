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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x86e94f65);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputUserContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(inputUserContact user_id:" + user_id + ")";
  }
}
