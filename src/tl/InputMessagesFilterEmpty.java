package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterEmpty extends tl.TMessagesFilter {

  
  public InputMessagesFilterEmpty(ByteBuffer buffer) {

  }
  
  public InputMessagesFilterEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x57e2f66c);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputMessagesFilterEmpty)";
  }
}
