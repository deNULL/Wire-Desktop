package tl;

import java.nio.ByteBuffer;

public class UserStatusEmpty extends tl.TUserStatus {

  
  public UserStatusEmpty(ByteBuffer buffer) {

  }
  
  public UserStatusEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9d05049);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(UserStatusEmpty)";
  }
}
