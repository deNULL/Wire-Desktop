package tl;

import java.nio.ByteBuffer;

public class InputUserEmpty extends tl.TInputUser {

  
  public InputUserEmpty(ByteBuffer buffer) {

  }
  
  public InputUserEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb98886cf);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputUserEmpty)";
  }
}
