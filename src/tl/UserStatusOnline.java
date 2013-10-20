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
    if (boxed) {
      buffer.putInt(0xedb93949);
    }
    buffer.putInt(expires);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UserStatusOnline expires:" + expires + ")";
  }
}
