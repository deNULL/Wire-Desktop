package tl;

import java.nio.ByteBuffer;

public class InputUserSelf extends tl.TInputUser {

  
  public InputUserSelf(ByteBuffer buffer) {

  }
  
  public InputUserSelf() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf7c1b13f);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputUserSelf)";
  }
}
