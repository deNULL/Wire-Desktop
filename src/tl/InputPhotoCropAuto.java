package tl;

import java.nio.ByteBuffer;

public class InputPhotoCropAuto extends tl.TInputPhotoCrop {

  
  public InputPhotoCropAuto(ByteBuffer buffer) {

  }
  
  public InputPhotoCropAuto() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xade6b004);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputPhotoCropAuto)";
  }
}
