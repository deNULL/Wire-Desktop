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
    if (boxed) {
      buffer.putInt(0x2331b22d);
    }
    buffer.putLong(id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(PhotoEmpty id:" + String.format("0x%016x", id) + ")";
  }
}
