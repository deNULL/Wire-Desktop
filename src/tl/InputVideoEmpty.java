package tl;

import java.nio.ByteBuffer;

public class InputVideoEmpty extends tl.TInputVideo {

  
  public InputVideoEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputVideoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5508ec75);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputVideoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputVideoEmpty)";
  }
}
