package tl;

import java.nio.ByteBuffer;

public class InputPeerForeign extends tl.TInputPeer {
  
  public InputPeerForeign(ByteBuffer buffer) {
    user_id = buffer.getInt();
    access_hash = buffer.getLong();
  }
  
  public InputPeerForeign(int user_id, long access_hash) {
    this.user_id = user_id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9b447325);
    }
    buffer.putInt(user_id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(InputPeerForeign user_id:" + user_id + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
