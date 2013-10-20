package tl;

import java.nio.ByteBuffer;

public class InputChatPhotoEmpty extends tl.TInputChatPhoto {

  
  public InputChatPhotoEmpty(ByteBuffer buffer) {

  }
  
  public InputChatPhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1ca48f57);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputChatPhotoEmpty)";
  }
}
