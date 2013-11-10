package tl;

import java.nio.ByteBuffer;

public class InputPeerForeign extends tl.TInputPeer {

  
  public InputPeerForeign(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    access_hash = buffer.getLong();
  }
  
  public InputPeerForeign(int user_id, long access_hash) {
    this.user_id = user_id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9b447325);
    }
    buffer.putInt(user_id);
    buffer.putLong(access_hash);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerForeign: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12;
  }
  
  public String toString() {
    return "(inputPeerForeign user_id:" + user_id + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
