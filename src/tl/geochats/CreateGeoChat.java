package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class CreateGeoChat extends tl.TLFunction {
  public String title;
  public tl.TInputGeoPoint geo_point;
  public String address;
  public String venue;
  
  public CreateGeoChat(ByteBuffer buffer) {
    try {  title = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
    try {  address = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  venue = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public CreateGeoChat(String title, tl.TInputGeoPoint geo_point, String address, String venue) {
    this.title = title;
    this.geo_point = geo_point;
    this.address = address;
    this.venue = venue;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe092e16);
    }
    try { TL.writeString(buffer, title.getBytes("UTF8"), false); } catch (Exception e) { };
    geo_point.writeTo(buffer, true);
    try { TL.writeString(buffer, address.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, venue.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at CreateGeoChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(title.getBytes()) + geo_point.length() + TL.length(address.getBytes()) + TL.length(venue.getBytes());
  }
  
  public String toString() {
    return "(geochats.createGeoChat title:" + "title" + " geo_point:" + geo_point + " address:" + "address" + " venue:" + "venue" + ")";
  }
}
