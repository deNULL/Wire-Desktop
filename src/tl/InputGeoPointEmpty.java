package tl;

import java.nio.ByteBuffer;

public class InputGeoPointEmpty extends tl.TInputGeoPoint {

  
  public InputGeoPointEmpty(ByteBuffer buffer) {

  }
  
  public InputGeoPointEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe4c123d6);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputGeoPointEmpty)";
  }
}
