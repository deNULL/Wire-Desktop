package tl;

import java.nio.ByteBuffer;

public class InputMediaUploadedPhoto extends tl.TInputMedia {
  
  public InputMediaUploadedPhoto(ByteBuffer buffer) {
    file = (tl.TInputFile) TL.read(buffer);
  }
  
  public InputMediaUploadedPhoto(tl.TInputFile file) {
    this.file = file;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2dc53a7d);
    }
    file.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + file.length();
  }
  
  public String toString() {
    return "(InputMediaUploadedPhoto file:" + file + ")";
  }
}
