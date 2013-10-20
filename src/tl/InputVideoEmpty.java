package tl;

import java.nio.ByteBuffer;

public class InputVideoEmpty extends tl.TInputVideo {

  
  public InputVideoEmpty(ByteBuffer buffer) {

  }
  
  public InputVideoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5508ec75);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputVideoEmpty)";
  }
}
