package tl;

import java.nio.ByteBuffer;

public class InputGeoPoint extends tl.TInputGeoPoint {
  
  public InputGeoPoint(ByteBuffer buffer) {
    lat = buffer.getDouble();
    lng = buffer.getDouble();
  }
  
  public InputGeoPoint(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf3b7acc9);
    }
    buffer.putDouble(lat);
    buffer.putDouble(lng);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(InputGeoPoint lat:" + lat + " lng:" + lng + ")";
  }
}
