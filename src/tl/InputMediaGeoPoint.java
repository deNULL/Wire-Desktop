package tl;

import java.nio.ByteBuffer;

public class InputMediaGeoPoint extends tl.TInputMedia {

  
  public InputMediaGeoPoint(ByteBuffer buffer) {
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
  }
  
  public InputMediaGeoPoint(tl.TInputGeoPoint geo_point) {
    this.geo_point = geo_point;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf9c44144);
    }
    geo_point.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaGeoPoint: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + geo_point.length();
  }
  
  public String toString() {
    return "(inputMediaGeoPoint geo_point:" + geo_point + ")";
  }
}
