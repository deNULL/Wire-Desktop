package tl;

import java.nio.ByteBuffer;

public class InputPeerContact extends tl.TInputPeer {
  
  public InputPeerContact(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public InputPeerContact(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1023dbe8);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(InputPeerContact user_id:" + user_id + ")";
  }
}
