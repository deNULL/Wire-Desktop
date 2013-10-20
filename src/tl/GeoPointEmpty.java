package tl;

import java.nio.ByteBuffer;

public class GeoPointEmpty extends tl.TGeoPoint {

  
  public GeoPointEmpty(ByteBuffer buffer) {

  }
  
  public GeoPointEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1117dd5f);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GeoPointEmpty)";
  }
}
