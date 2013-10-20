package tl;

import java.nio.ByteBuffer;

public class InputMediaPhoto extends tl.TInputMedia {
  public tl.TInputPhoto id;
  
  public InputMediaPhoto(ByteBuffer buffer) {
    id = (tl.TInputPhoto) TL.read(buffer);
  }
  
  public InputMediaPhoto(tl.TInputPhoto id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8f2ab2ec);
    }
    id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(InputMediaPhoto id:" + id + ")";
  }
}
