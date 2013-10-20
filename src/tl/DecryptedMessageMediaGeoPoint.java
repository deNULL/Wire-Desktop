package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaGeoPoint extends tl.TDecryptedMessageMedia {
  
  public DecryptedMessageMediaGeoPoint(ByteBuffer buffer) {
    lat = buffer.getDouble();
    lng = buffer.getDouble();
  }
  
  public DecryptedMessageMediaGeoPoint(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x35480a59);
    }
    buffer.putDouble(lat);
    buffer.putDouble(lng);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(DecryptedMessageMediaGeoPoint lat:" + lat + " lng:" + lng + ")";
  }
}
