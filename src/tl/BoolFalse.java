package tl;

import java.nio.ByteBuffer;

public class BoolFalse extends tl.TBool {

  
  public BoolFalse(ByteBuffer buffer) throws Exception {

  }
  
  public BoolFalse() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xbc799737);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BoolFalse: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(boolFalse)";
  }
}
