package tl;

import java.nio.ByteBuffer;

public class UserSelf extends tl.TUser {
  
  public UserSelf(ByteBuffer buffer) {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
    phone = new String(TL.readString(buffer));
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x720535ec);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
    TL.writeString(buffer, phone.getBytes(), false);
    photo.writeTo(buffer, false);
    status.writeTo(buffer, false);
    buffer.putInt(inactive ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes()) + TL.length(phone.getBytes()) + photo.length() + status.length();
  }
  
  public String toString() {
    return "(UserSelf id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + " phone:" + "phone" + " photo:" + photo + " status:" + status + " inactive:" + (inactive ? "true" : "false") + ")";
  }
}
