package tl;

import java.nio.ByteBuffer;

public class InputMediaPhoto extends tl.TInputMedia {
  public tl.TInputPhoto id;
  
  public InputMediaPhoto(ByteBuffer buffer) throws Exception {
    id = (tl.TInputPhoto) TL.read(buffer);
  }
  
  public InputMediaPhoto(tl.TInputPhoto id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8f2ab2ec);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(inputMediaPhoto id:" + id + ")";
  }
}
