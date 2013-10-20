package tl;

import java.nio.ByteBuffer;

public class InputMediaEmpty extends tl.TInputMedia {

  
  public InputMediaEmpty(ByteBuffer buffer) {

  }
  
  public InputMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9664f57f);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputMediaEmpty)";
  }
}
