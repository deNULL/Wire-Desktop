package tl;

public abstract class TUpdate extends tl.TLObject {
  public tl.contacts.TMyLink my_link;
  public boolean previous;
  public String location;
  public tl.TUserStatus status;
  public tl.TEncryptedChat chat;
  public int qts;
  public tl.contacts.TForeignLink foreign_link;
  public int date;
  public long auth_key_id;
  public tl.TChatParticipants participants;
  public tl.TUserProfilePhoto photo;
  public int[] messages;
  public int id;
  public int chat_id;
  public int max_date;
  public String first_name;
  public String device;
  public String last_name;
  public int user_id;
  public long random_id;
  public int pts;
}
