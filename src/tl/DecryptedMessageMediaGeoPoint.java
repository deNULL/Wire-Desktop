package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaGeoPoint extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaGeoPoint(ByteBuffer buffer) throws Exception {
    lat = buffer.getDouble();
    lng = buffer.getDouble();
  }
  
  public DecryptedMessageMediaGeoPoint(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x35480a59);
    }
    buffer.putDouble(lat);
    buffer.putDouble(lng);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageMediaGeoPoint: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(decryptedMessageMediaGeoPoint lat:" + lat + " lng:" + lng + ")";
  }
}
