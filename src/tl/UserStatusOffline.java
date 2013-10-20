package tl;

import java.nio.ByteBuffer;

public class UserStatusOffline extends tl.TUserStatus {
  
  public UserStatusOffline(ByteBuffer buffer) {
    was_online = buffer.getInt();
  }
  
  public UserStatusOffline(int was_online) {
    this.was_online = was_online;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8c703f);
    }
    buffer.putInt(was_online);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UserStatusOffline was_online:" + was_online + ")";
  }
}
