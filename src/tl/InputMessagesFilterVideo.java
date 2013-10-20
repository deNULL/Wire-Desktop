package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterVideo extends tl.TMessagesFilter {

  
  public InputMessagesFilterVideo(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterVideo() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9fc00e65);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMessagesFilterVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputMessagesFilterVideo)";
  }
}
