package tl;

import java.nio.ByteBuffer;

public class GeoPoint extends tl.TGeoPoint {

  
  public GeoPoint(ByteBuffer buffer) throws Exception {
    lng = buffer.getDouble();
    lat = buffer.getDouble();
  }
  
  public GeoPoint(double lng, double lat) {
    this.lng = lng;
    this.lat = lat;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2049d70c);
    }
    buffer.putDouble(lng);
    buffer.putDouble(lat);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GeoPoint: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(geoPoint lng:" + lng + " lat:" + lat + ")";
  }
}
