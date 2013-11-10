package tl;

import java.nio.ByteBuffer;

public class InputMessagesFilterEmpty extends tl.TMessagesFilter {

  
  public InputMessagesFilterEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputMessagesFilterEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x57e2f66c);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMessagesFilterEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputMessagesFilterEmpty)";
  }
}
