package tl;

import java.nio.ByteBuffer;

public class InputPeerContact extends tl.TInputPeer {

  
  public InputPeerContact(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
  }
  
  public InputPeerContact(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1023dbe8);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(inputPeerContact user_id:" + user_id + ")";
  }
}
