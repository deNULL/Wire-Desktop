package tl;

import java.nio.ByteBuffer;

public class BoolTrue extends tl.TBool {

  
  public BoolTrue(ByteBuffer buffer) throws Exception {

  }
  
  public BoolTrue() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x997275b5);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BoolTrue: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(boolTrue)";
  }
}
