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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe22045fc);
    }
    buffer.putLong(session_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DestroySessionOk: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(destroy_session_ok session_id:" + String.format("0x%016x", session_id) + ")";
  }
}
