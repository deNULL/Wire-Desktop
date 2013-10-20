package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterPhotoVideo extends tl.TMessagesFilter {

  
  public InputMessagesFilterPhotoVideo(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterPhotoVideo() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x56e9f0e4);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputMessagesFilterPhotoVideo)";
  }
}
