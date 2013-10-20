package tl;

import java.nio.ByteBuffer;

public class InputMediaVideo extends tl.TInputMedia {
  public tl.TInputVideo id;
  
  public InputMediaVideo(ByteBuffer buffer) {
    id = (tl.TInputVideo) TL.read(buffer);
  }
  
  public InputMediaVideo(tl.TInputVideo id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7f023ae6);
    }
    id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(InputMediaVideo id:" + id + ")";
  }
}
