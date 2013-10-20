package tl;

import java.nio.ByteBuffer;

public class UserFull extends tl.TUserFull {

  
  public UserFull(ByteBuffer buffer) {
    user = (tl.TUser) TL.read(buffer);
    link = (tl.contacts.TLink) TL.read(buffer);
    profile_photo = (tl.TPhoto) TL.read(buffer);
    notify_settings = (tl.TPeerNotifySettings) TL.read(buffer);
    blocked = (buffer.getInt() == 0x997275b5);
    real_first_name = new String(TL.readString(buffer));
    real_last_name = new String(TL.readString(buffer));
  }
  
  public UserFull(tl.TUser user, tl.contacts.TLink link, tl.TPhoto profile_photo, tl.TPeerNotifySettings notify_settings, boolean blocked, String real_first_name, String real_last_name) {
    this.user = user;
    this.link = link;
    this.profile_photo = profile_photo;
    this.notify_settings = notify_settings;
    this.blocked = blocked;
    this.real_first_name = real_first_name;
    this.real_last_name = real_last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x771095da);
    }
    user.writeTo(buffer, true);
    link.writeTo(buffer, true);
    profile_photo.writeTo(buffer, true);
    notify_settings.writeTo(buffer, true);
    buffer.putInt(blocked ? 0x997275b5 : 0xbc799737);
    TL.writeString(buffer, real_first_name.getBytes(), false);
    TL.writeString(buffer, real_last_name.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserFull: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + user.length() + link.length() + profile_photo.length() + notify_settings.length() + TL.length(real_first_name.getBytes()) + TL.length(real_last_name.getBytes());
  }
  
  public String toString() {
    return "(userFull user:" + user + " link:" + link + " profile_photo:" + profile_photo + " notify_settings:" + notify_settings + " blocked:" + (blocked ? "true" : "false") + " real_first_name:" + "real_first_name" + " real_last_name:" + "real_last_name" + ")";
  }
}
