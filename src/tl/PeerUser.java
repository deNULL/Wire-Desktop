package tl;

import java.nio.ByteBuffer;

public class PeerUser extends tl.TPeer {
  
  public PeerUser(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public PeerUser(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9db1bc6d);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(PeerUser user_id:" + user_id + ")";
  }
}
