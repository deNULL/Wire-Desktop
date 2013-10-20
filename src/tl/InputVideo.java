package tl;

import java.nio.ByteBuffer;

public class InputVideo extends tl.TInputVideo {

  
  public InputVideo(ByteBuffer buffer) {
    id = buffer.getLong();
    access_hash = buffer.getLong();
  }
  
  public InputVideo(long id, long access_hash) {
    this.id = id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xee579652);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(inputVideo id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
