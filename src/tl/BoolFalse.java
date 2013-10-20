package tl;

import java.nio.ByteBuffer;

public class BoolFalse extends tl.TBool {

  
  public BoolFalse(ByteBuffer buffer) {

  }
  
  public BoolFalse() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xbc799737);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(BoolFalse)";
  }
}
