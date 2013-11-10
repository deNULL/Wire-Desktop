package tl;

import java.nio.ByteBuffer;

public class UserContact extends tl.TUser {

  
  public UserContact(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
    access_hash = buffer.getLong();
    phone = new String(TL.readString(buffer), "UTF8");
    photo = (tl.TUserProfilePhoto) TL.read(buffer);
    status = (tl.TUserStatus) TL.read(buffer);
  }
  
  public UserContact(int id, String first_name, String last_name, long access_hash, String phone, tl.TUserProfilePhoto photo, tl.TUserStatus status) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.access_hash = access_hash;
    this.phone = phone;
    this.photo = photo;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf2fb8319);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    buffer.putLong(access_hash);
    TL.writeString(buffer, phone.getBytes("UTF8"), false);
    photo.writeTo(buffer, true);
    status.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8")) + TL.length(phone.getBytes("UTF8")) + photo.length() + status.length();
  }
  
  public String toString() {
    return "(userContact id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + " access_hash:" + String.format("0x%016x", access_hash) + " phone:" + "phone" + " photo:" + photo + " status:" + status + ")";
  }
}
