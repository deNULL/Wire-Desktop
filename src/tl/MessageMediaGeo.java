package tl;

import java.nio.ByteBuffer;

public class MessageMediaGeo extends tl.TMessageMedia {

  
  public MessageMediaGeo(ByteBuffer buffer) throws Exception {
    geo = (tl.TGeoPoint) TL.read(buffer);
  }
  
  public MessageMediaGeo(tl.TGeoPoint geo) {
    this.geo = geo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x56e0d474);
    }
    geo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaGeo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + geo.length();
  }
  
  public String toString() {
    return "(messageMediaGeo geo:" + geo + ")";
  }
}
