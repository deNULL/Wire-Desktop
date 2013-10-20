package tl;

public abstract class TUser extends tl.TLObject {
  public int id;
  public boolean inactive;
  public String first_name;
  public String phone;
  public long access_hash;
  public tl.TUserStatus status;
  public String last_name;
  public tl.TUserProfilePhoto photo;
}
