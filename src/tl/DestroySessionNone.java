package tl;

import java.nio.ByteBuffer;

public class DestroySessionNone extends tl.TDestroySessionRes {
  
  public DestroySessionNone(ByteBuffer buffer) {
    session_id = buffer.getLong();
  }
  
  public DestroySessionNone(long session_id) {
    this.session_id = session_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x62d350c9);
    }
    buffer.putLong(session_id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(DestroySessionNone session_id:" + String.format("0x%016x", session_id) + ")";
  }
}
