package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class Photo extends tl.photos.TPhoto {
  
  public Photo(ByteBuffer buffer) {
    photo = (tl.TPhoto) TL.read(buffer);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Photo(tl.TPhoto photo, tl.TUser[] users) {
    this.photo = photo;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x20212ca8);
    }
    photo.writeTo(buffer, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + photo.length() + TL.length(users);
  }
  
  public String toString() {
    return "(Photo photo:" + photo + " users:" + TL.toString(users) + ")";
  }
}
