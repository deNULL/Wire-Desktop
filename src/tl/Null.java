package tl;

import java.nio.ByteBuffer;

public class Null extends tl.TNull {

  
  public Null(ByteBuffer buffer) {

  }
  
  public Null() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x56730bcc);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(Null)";
  }
}
