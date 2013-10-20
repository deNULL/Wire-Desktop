package tl;

import java.nio.ByteBuffer;

public class InputPhotoCropAuto extends tl.TInputPhotoCrop {

  
  public InputPhotoCropAuto(ByteBuffer buffer) {

  }
  
  public InputPhotoCropAuto() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xade6b004);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPhotoCropAuto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPhotoCropAuto)";
  }
}
