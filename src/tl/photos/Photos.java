package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class Photos extends tl.photos.TPhotos {
  
  public Photos(ByteBuffer buffer) {
    photos = TL.readVector(buffer, true, new tl.TPhoto[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Photos(tl.TPhoto[] photos, tl.TUser[] users) {
    this.photos = photos;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8dca6aa5);
    }
    TL.writeVector(buffer, photos, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(photos) + TL.length(users);
  }
  
  public String toString() {
    return "(Photos photos:" + TL.toString(photos) + " users:" + TL.toString(users) + ")";
  }
}
