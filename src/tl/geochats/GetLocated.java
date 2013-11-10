package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class GetLocated extends tl.TLFunction {
  public tl.TInputGeoPoint geo_point;
  public int radius;
  public int limit;
  
  public GetLocated(ByteBuffer buffer) throws Exception {
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
    radius = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetLocated(tl.TInputGeoPoint geo_point, int radius, int limit) {
    this.geo_point = geo_point;
    this.radius = radius;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7f192d8f);
    }
    geo_point.writeTo(buffer, true);
    buffer.putInt(radius);
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetLocated: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + geo_point.length();
  }
  
  public String toString() {
    return "(geochats.getLocated geo_point:" + geo_point + " radius:" + radius + " limit:" + limit + ")";
  }
}
