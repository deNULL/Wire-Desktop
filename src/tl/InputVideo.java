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
    if (boxed) {
      buffer.putInt(0xee579652);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(InputVideo id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
