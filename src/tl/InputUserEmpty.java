package tl;

import java.nio.ByteBuffer;

public class InputUserEmpty extends tl.TInputUser {

  
  public InputUserEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputUserEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb98886cf);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputUserEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputUserEmpty)";
  }
}
