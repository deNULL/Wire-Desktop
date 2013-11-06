package tl;

import java.nio.ByteBuffer;

public class UserRequest extends tl.TUser {

  
  public UserRequest(ByteBuffer buffer) {
    id = buffer.getInt();
    try {  first_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  last_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    access_hash = buffer.getLong();
    try {  phone = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    photo = (tl.TUserProfilePhoto) TL.read(buffer);
    status = (tl.TUserStatus) TL.read(buffer);
  }
  
  public UserRequest(int id, String first_name, String last_name, long access_hash, String phone, tl.TUserProfilePhoto photo, tl.TUserStatus status) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.access_hash = access_hash;
    this.phone = phone;
    this.photo = photo;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x22e8ceb0);
    }
    buffer.putInt(id);
    try { TL.writeString(buffer, first_name.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, last_name.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putLong(access_hash);
    try { TL.writeString(buffer, phone.getBytes("UTF8"), false); } catch (Exception e) { };
    photo.writeTo(buffer, true);
    status.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserRequest: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes()) + TL.length(phone.getBytes()) + photo.length() + status.length();
  }
  
  public String toString() {
    return "(userRequest id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + " access_hash:" + String.format("0x%016x", access_hash) + " phone:" + "phone" + " photo:" + photo + " status:" + status + ")";
  }
}
