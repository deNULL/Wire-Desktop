package tl;

import java.nio.ByteBuffer;

public class BoolTrue extends tl.TBool {

  
  public BoolTrue(ByteBuffer buffer) {

  }
  
  public BoolTrue() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x997275b5);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BoolTrue: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(boolTrue)";
  }
}
