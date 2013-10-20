package tl;

import java.nio.ByteBuffer;

public class UpdateUserPhoto extends tl.TUpdate {
  
  public UpdateUserPhoto(ByteBuffer buffer) {
    user_id = buffer.getInt();
    date = buffer.getInt();
    photo = (tl.TUserProfilePhoto) TL.read(buffer);
    previous = (buffer.getInt() == 0x997275b5);
  }
  
  public UpdateUserPhoto(int user_id, int date, tl.TUserProfilePhoto photo, boolean previous) {
    this.user_id = user_id;
    this.date = date;
    this.photo = photo;
    this.previous = previous;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x95313b0c);
    }
    buffer.putInt(user_id);
    buffer.putInt(date);
    photo.writeTo(buffer, false);
    buffer.putInt(previous ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 16 + photo.length();
  }
  
  public String toString() {
    return "(UpdateUserPhoto user_id:" + user_id + " date:" + date + " photo:" + photo + " previous:" + (previous ? "true" : "false") + ")";
  }
}
