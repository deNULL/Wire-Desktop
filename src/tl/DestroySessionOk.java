package tl;

import java.nio.ByteBuffer;

public class DestroySessionOk extends tl.TDestroySessionRes {
  
  public DestroySessionOk(ByteBuffer buffer) {
    session_id = buffer.getLong();
  }
  
  public DestroySessionOk(long session_id) {
    this.session_id = session_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe22045fc);
    }
    buffer.putLong(session_id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(DestroySessionOk session_id:" + String.format("0x%016x", session_id) + ")";
  }
}
