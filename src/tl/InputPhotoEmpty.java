package tl;

import java.nio.ByteBuffer;

public class InputPhotoEmpty extends tl.TInputPhoto {

  
  public InputPhotoEmpty(ByteBuffer buffer) {

  }
  
  public InputPhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1cd7bf0d);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPhotoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPhotoEmpty)";
  }
}
