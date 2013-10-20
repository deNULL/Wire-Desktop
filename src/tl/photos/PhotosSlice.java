package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class PhotosSlice extends tl.photos.TPhotos {
  
  public PhotosSlice(ByteBuffer buffer) {
    count = buffer.getInt();
    photos = TL.readVector(buffer, true, new tl.TPhoto[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public PhotosSlice(int count, tl.TPhoto[] photos, tl.TUser[] users) {
    this.count = count;
    this.photos = photos;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x15051f54);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, photos, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(photos) + TL.length(users);
  }
  
  public String toString() {
    return "(PhotosSlice count:" + count + " photos:" + TL.toString(photos) + " users:" + TL.toString(users) + ")";
  }
}
