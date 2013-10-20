package tl;

import java.nio.ByteBuffer;

public class GeoPoint extends tl.TGeoPoint {
  
  public GeoPoint(ByteBuffer buffer) {
    lng = buffer.getDouble();
    lat = buffer.getDouble();
  }
  
  public GeoPoint(double lng, double lat) {
    this.lng = lng;
    this.lat = lat;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2049d70c);
    }
    buffer.putDouble(lng);
    buffer.putDouble(lat);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(GeoPoint lng:" + lng + " lat:" + lat + ")";
  }
}
