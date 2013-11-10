package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterPhotos extends tl.TMessagesFilter {

  
  public InputMessagesFilterPhotos(ByteBuffer buffer) throws Exception {

  }
  
  public InputMessagesFilterPhotos() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9609a51c);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMessagesFilterPhotos: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputMessagesFilterPhotos)";
  }
}
