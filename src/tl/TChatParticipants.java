package tl;

public abstract class TChatParticipants extends tl.TLObject {
  public int chat_id;
  public int admin_id;
  public tl.TChatParticipant[] participants;
  public int version;
}
