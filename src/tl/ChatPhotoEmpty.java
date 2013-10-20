package tl;

import java.nio.ByteBuffer;

public class ChatPhotoEmpty extends tl.TChatPhoto {

  
  public ChatPhotoEmpty(ByteBuffer buffer) {

  }
  
  public ChatPhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x37c1011c);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ChatPhotoEmpty)";
  }
}
