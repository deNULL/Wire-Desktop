package tl;

import java.nio.ByteBuffer;

public class MessageMediaGeo extends tl.TMessageMedia {
  
  public MessageMediaGeo(ByteBuffer buffer) {
    geo = (tl.TGeoPoint) TL.read(buffer);
  }
  
  public MessageMediaGeo(tl.TGeoPoint geo) {
    this.geo = geo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x56e0d474);
    }
    geo.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + geo.length();
  }
  
  public String toString() {
    return "(MessageMediaGeo geo:" + geo + ")";
  }
}
