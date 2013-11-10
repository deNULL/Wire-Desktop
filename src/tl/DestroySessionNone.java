package tl;

import java.nio.ByteBuffer;

public class DestroySessionNone extends tl.TDestroySessionRes {

  
  public DestroySessionNone(ByteBuffer buffer) throws Exception {
    session_id = buffer.getLong();
  }
  
  public DestroySessionNone(long session_id) {
    this.session_id = session_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x62d350c9);
    }
    buffer.putLong(session_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DestroySessionNone: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(destroy_session_none session_id:" + String.format("0x%016x", session_id) + ")";
  }
}
