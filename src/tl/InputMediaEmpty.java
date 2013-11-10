package tl;

import java.nio.ByteBuffer;

public class InputMediaEmpty extends tl.TInputMedia {

  
  public InputMediaEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9664f57f);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputMediaEmpty)";
  }
}
