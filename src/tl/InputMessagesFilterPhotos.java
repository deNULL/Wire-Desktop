package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterPhotos extends tl.TMessagesFilter {

  
  public InputMessagesFilterPhotos(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterPhotos() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9609a51c);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputMessagesFilterPhotos)";
  }
}
