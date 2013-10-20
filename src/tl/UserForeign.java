package tl;

import java.nio.ByteBuffer;

public class UserForeign extends tl.TUser {

  
  public UserForeign(ByteBuffer buffer) {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
    access_hash = buffer.getLong();
    photo = (tl.TUserProfilePhoto) TL.read(buffer);
    status = (tl.TUserStatus) TL.read(buffer);
  }
  
  public UserForeign(int id, String first_name, String last_name, long access_hash, tl.TUserProfilePhoto photo, tl.TUserStatus status) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.access_hash = access_hash;
    this.photo = photo;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5214c89d);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
    buffer.putLong(access_hash);
    photo.writeTo(buffer, true);
    status.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserForeign: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes()) + photo.length() + status.length();
  }
  
  public String toString() {
    return "(userForeign id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + " access_hash:" + String.format("0x%016x", access_hash) + " photo:" + photo + " status:" + status + ")";
  }
}
