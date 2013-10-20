package tl;

import java.nio.ByteBuffer;

public class UserStatusOnline extends tl.TUserStatus {

  
  public UserStatusOnline(ByteBuffer buffer) {
    expires = buffer.getInt();
  }
  
  public UserStatusOnline(int expires) {
    this.expires = expires;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xedb93949);
    }
    buffer.putInt(expires);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserStatusOnline: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(userStatusOnline expires:" + expires + ")";
  }
}
