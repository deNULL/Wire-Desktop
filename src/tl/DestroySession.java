package tl;

import java.nio.ByteBuffer;

public class DestroySession extends TLFunction {
  public long session_id;
  
  public DestroySession(ByteBuffer buffer) {
    session_id = buffer.getLong();
  }
  
  public DestroySession(long session_id) {
    this.session_id = session_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe7512126);
    }
    buffer.putLong(session_id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(DestroySession session_id:" + String.format("0x%016x", session_id) + ")";
  }
}
