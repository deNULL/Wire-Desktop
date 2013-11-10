package tl;

import java.nio.ByteBuffer;

public class GeoChat extends tl.TChat {

  
  public GeoChat(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    access_hash = buffer.getLong();
    title = new String(TL.readString(buffer), "UTF8");
    address = new String(TL.readString(buffer), "UTF8");
    venue = new String(TL.readString(buffer), "UTF8");
    geo = (tl.TGeoPoint) TL.read(buffer);
    photo = (tl.TChatPhoto) TL.read(buffer);
    participants_count = buffer.getInt();
    date = buffer.getInt();
    checked_in = (buffer.getInt() == 0x997275b5);
    version = buffer.getInt();
  }
  
  public GeoChat(int id, long access_hash, String title, String address, String venue, tl.TGeoPoint geo, tl.TChatPhoto photo, int participants_count, int date, boolean checked_in, int version) {
    this.id = id;
    this.access_hash = access_hash;
    this.title = title;
    this.address = address;
    this.venue = venue;
    this.geo = geo;
    this.photo = photo;
    this.participants_count = participants_count;
    this.date = date;
    this.checked_in = checked_in;
    this.version = version;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x75eaea5a);
    }
    buffer.putInt(id);
    buffer.putLong(access_hash);
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    TL.writeString(buffer, address.getBytes("UTF8"), false);
    TL.writeString(buffer, venue.getBytes("UTF8"), false);
    geo.writeTo(buffer, true);
    photo.writeTo(buffer, true);
    buffer.putInt(participants_count);
    buffer.putInt(date);
    buffer.putInt(checked_in ? 0x997275b5 : 0xbc799737);
    buffer.putInt(version);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GeoChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 36 + TL.length(title.getBytes("UTF8")) + TL.length(address.getBytes("UTF8")) + TL.length(venue.getBytes("UTF8")) + geo.length() + photo.length();
  }
  
  public String toString() {
    return "(geoChat id:" + id + " access_hash:" + String.format("0x%016x", access_hash) + " title:" + "title" + " address:" + "address" + " venue:" + "venue" + " geo:" + geo + " photo:" + photo + " participants_count:" + participants_count + " date:" + date + " checked_in:" + (checked_in ? "true" : "false") + " version:" + version + ")";
  }
}
