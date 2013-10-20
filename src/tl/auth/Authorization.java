package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class Authorization extends tl.auth.TAuthorization {

  
  public Authorization(ByteBuffer buffer) {
    expires = buffer.getInt();
    user = (tl.TUser) TL.read(buffer);
  }
  
  public Authorization(int expires, tl.TUser user) {
    this.expires = expires;
    this.user = user;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf6b673a4);
    }
    buffer.putInt(expires);
    user.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Authorization: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + user.length();
  }
  
  public String toString() {
    return "(auth.authorization expires:" + expires + " user:" + user + ")";
  }
}
