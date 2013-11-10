package tl;

import java.nio.ByteBuffer;

public class GeoPointEmpty extends tl.TGeoPoint {

  
  public GeoPointEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public GeoPointEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1117dd5f);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GeoPointEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(geoPointEmpty)";
  }
}
