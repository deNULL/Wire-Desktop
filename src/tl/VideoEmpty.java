package tl;

import java.nio.ByteBuffer;

public class VideoEmpty extends tl.TVideo {
  
  public VideoEmpty(ByteBuffer buffer) {
    id = buffer.getLong();
  }
  
  public VideoEmpty(long id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc10658a8);
    }
    buffer.putLong(id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(VideoEmpty id:" + String.format("0x%016x", id) + ")";
  }
}
