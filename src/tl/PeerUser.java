package tl;

import java.nio.ByteBuffer;

public class PeerUser extends tl.TPeer {

  
  public PeerUser(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
  }
  
  public PeerUser(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9db1bc6d);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PeerUser: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(peerUser user_id:" + user_id + ")";
  }
}
