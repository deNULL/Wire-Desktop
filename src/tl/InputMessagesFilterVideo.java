package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterVideo extends tl.TMessagesFilter {

  
  public InputMessagesFilterVideo(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterVideo() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9fc00e65);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputMessagesFilterVideo)";
  }
}
