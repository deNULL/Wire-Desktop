package tl;

import java.nio.ByteBuffer;

public class VideoEmpty extends tl.TVideo {

  
  public VideoEmpty(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
  }
  
  public VideoEmpty(long id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc10658a8);
    }
    buffer.putLong(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at VideoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(videoEmpty id:" + String.format("0x%016x", id) + ")";
  }
}
