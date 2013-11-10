package tl;

import java.nio.ByteBuffer;

public class Photo extends tl.TPhoto {

  
  public Photo(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
    access_hash = buffer.getLong();
    user_id = buffer.getInt();
    date = buffer.getInt();
    caption = new String(TL.readString(buffer), "UTF8");
    geo = (tl.TGeoPoint) TL.read(buffer);
    sizes = TL.readVector(buffer, true, new tl.TPhotoSize[0]);
  }
  
  public Photo(long id, long access_hash, int user_id, int date, String caption, tl.TGeoPoint geo, tl.TPhotoSize[] sizes) {
    this.id = id;
    this.access_hash = access_hash;
    this.user_id = user_id;
    this.date = date;
    this.caption = caption;
    this.geo = geo;
    this.sizes = sizes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x22b56751);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
    buffer.putInt(user_id);
    buffer.putInt(date);
    TL.writeString(buffer, caption.getBytes("UTF8"), false);
    geo.writeTo(buffer, true);
    TL.writeVector(buffer, sizes, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Photo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 36 + TL.length(caption.getBytes("UTF8")) + geo.length() + TL.length(sizes);
  }
  
  public String toString() {
    return "(photo id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + " user_id:" + user_id + " date:" + date + " caption:" + "caption" + " geo:" + geo + " sizes:" + TL.toString(sizes) + ")";
  }
}
