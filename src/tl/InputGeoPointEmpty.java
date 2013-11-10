package tl;

import java.nio.ByteBuffer;

public class InputGeoPointEmpty extends tl.TInputGeoPoint {

  
  public InputGeoPointEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputGeoPointEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe4c123d6);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputGeoPointEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputGeoPointEmpty)";
  }
}
