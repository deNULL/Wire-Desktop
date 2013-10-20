package tl;

public abstract class TChatFull extends tl.TLObject {
  public int id;
  public tl.TPhoto chat_photo;
  public tl.TPeerNotifySettings notify_settings;
  public tl.TChatParticipants participants;
}
