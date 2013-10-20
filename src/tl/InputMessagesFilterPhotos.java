package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterPhotos extends tl.TMessagesFilter {

  
  public InputMessagesFilterPhotos(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterPhotos() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9609a51c);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMessagesFilterPhotos: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputMessagesFilterPhotos)";
  }
}
