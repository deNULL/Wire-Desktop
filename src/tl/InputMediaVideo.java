package tl;

import java.nio.ByteBuffer;

public class InputMediaVideo extends tl.TInputMedia {
  public tl.TInputPhoto id;
  
  public InputMediaVideo(ByteBuffer buffer) {
    id = (tl.TInputPhoto) TL.read(buffer);
  }
  
  public InputMediaVideo(tl.TInputPhoto id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7f023ae6);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(inputMediaVideo id:" + id + ")";
  }
}
