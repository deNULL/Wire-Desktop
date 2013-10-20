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
    if (boxed) {
      buffer.putInt(0xf6b673a4);
    }
    buffer.putInt(expires);
    user.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + user.length();
  }
  
  public String toString() {
    return "(Authorization expires:" + expires + " user:" + user + ")";
  }
}
