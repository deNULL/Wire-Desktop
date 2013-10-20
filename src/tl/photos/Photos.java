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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8dca6aa5);
    }
    TL.writeVector(buffer, photos, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Photos: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(photos) + TL.length(users);
  }
  
  public String toString() {
    return "(photos.photos photos:" + TL.toString(photos) + " users:" + TL.toString(users) + ")";
  }
}
