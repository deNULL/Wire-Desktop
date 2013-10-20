package tl;

import java.nio.ByteBuffer;

public class BoolTrue extends tl.TBool {

  
  public BoolTrue(ByteBuffer buffer) {

  }
  
  public BoolTrue() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x997275b5);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(BoolTrue)";
  }
}
