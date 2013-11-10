package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class CreateGeoChat extends tl.TLFunction {
  public String title;
  public tl.TInputGeoPoint geo_point;
  public String address;
  public String venue;
  
  public CreateGeoChat(ByteBuffer buffer) throws Exception {
    title = new String(TL.readString(buffer), "UTF8");
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
    address = new String(TL.readString(buffer), "UTF8");
    venue = new String(TL.readString(buffer), "UTF8");
  }
  
  public CreateGeoChat(String title, tl.TInputGeoPoint geo_point, String address, String venue) {
    this.title = title;
    this.geo_point = geo_point;
    this.address = address;
    this.venue = venue;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe092e16);
    }
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    geo_point.writeTo(buffer, true);
    TL.writeString(buffer, address.getBytes("UTF8"), false);
    TL.writeString(buffer, venue.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at CreateGeoChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(title.getBytes("UTF8")) + geo_point.length() + TL.length(address.getBytes("UTF8")) + TL.length(venue.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(geochats.createGeoChat title:" + "title" + " geo_point:" + geo_point + " address:" + "address" + " venue:" + "venue" + ")";
  }
}
