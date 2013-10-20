package tl;

import java.nio.ByteBuffer;

public class InputChatPhotoEmpty extends tl.TInputChatPhoto {

  
  public InputChatPhotoEmpty(ByteBuffer buffer) {

  }
  
  public InputChatPhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1ca48f57);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputChatPhotoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputChatPhotoEmpty)";
  }
}
