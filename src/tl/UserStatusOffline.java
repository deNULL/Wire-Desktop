package tl;

import java.nio.ByteBuffer;

public class UserStatusOffline extends tl.TUserStatus {

  
  public UserStatusOffline(ByteBuffer buffer) throws Exception {
    was_online = buffer.getInt();
  }
  
  public UserStatusOffline(int was_online) {
    this.was_online = was_online;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8c703f);
    }
    buffer.putInt(was_online);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserStatusOffline: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(userStatusOffline was_online:" + was_online + ")";
  }
}
