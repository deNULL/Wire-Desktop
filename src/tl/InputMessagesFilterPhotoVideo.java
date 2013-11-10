package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterPhotoVideo extends tl.TMessagesFilter {

  
  public InputMessagesFilterPhotoVideo(ByteBuffer buffer) throws Exception {

  }
  
  public InputMessagesFilterPhotoVideo() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x56e9f0e4);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMessagesFilterPhotoVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputMessagesFilterPhotoVideo)";
  }
}
