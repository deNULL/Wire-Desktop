package tl;

public abstract class TUserFull extends tl.TLObject {
  public String real_first_name;
  public tl.TPhoto profile_photo;
  public String real_last_name;
  public tl.contacts.TLink link;
  public boolean blocked;
  public tl.TPeerNotifySettings notify_settings;
  public tl.TUser user;
}
