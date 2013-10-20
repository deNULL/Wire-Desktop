package tl;

import java.nio.ByteBuffer;

public class PhotoEmpty extends tl.TPhoto {

  
  public PhotoEmpty(ByteBuffer buffer) {
    id = buffer.getLong();
  }
  
  public PhotoEmpty(long id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2331b22d);
    }
    buffer.putLong(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PhotoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(photoEmpty id:" + String.format("0x%016x", id) + ")";
  }
}
