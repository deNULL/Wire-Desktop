package tl;

import java.nio.ByteBuffer;

public class InputUserSelf extends tl.TInputUser {

  
  public InputUserSelf(ByteBuffer buffer) {

  }
  
  public InputUserSelf() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf7c1b13f);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputUserSelf: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputUserSelf)";
  }
}
