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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2dc53a7d);
    }
    file.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaUploadedPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + file.length();
  }
  
  public String toString() {
    return "(inputMediaUploadedPhoto file:" + file + ")";
  }
}
