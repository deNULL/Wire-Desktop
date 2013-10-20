package tl;

import java.nio.ByteBuffer;

public class UserStatusEmpty extends tl.TUserStatus {

  
  public UserStatusEmpty(ByteBuffer buffer) {

  }
  
  public UserStatusEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9d05049);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserStatusEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(userStatusEmpty)";
  }
}
