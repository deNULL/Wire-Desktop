package tl;

import java.nio.ByteBuffer;

public class UserSelf extends tl.TUser {

  
  public UserSelf(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
    phone = new String(TL.readString(buffer), "UTF8");
    photo = (tl.TUserProfilePhoto) TL.read(buffer);
    status = (tl.TUserStatus) TL.read(buffer);
    inactive = (buffer.getInt() == 0x997275b5);
  }
  
  public UserSelf(int id, String first_name, String last_name, String phone, tl.TUserProfilePhoto photo, tl.TUserStatus status, boolean inactive) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.phone = phone;
    this.photo = photo;
    this.status = status;
    this.inactive = inactive;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x720535ec);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    TL.writeString(buffer, phone.getBytes("UTF8"), false);
    photo.writeTo(buffer, true);
    status.writeTo(buffer, true);
    buffer.putInt(inactive ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserSelf: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8")) + TL.length(phone.getBytes("UTF8")) + photo.length() + status.length();
  }
  
  public String toString() {
    return "(userSelf id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + " phone:" + "phone" + " photo:" + photo + " status:" + status + " inactive:" + (inactive ? "true" : "false") + ")";
  }
}
