package tl.messages;

public abstract class TChatFull extends tl.TLObject {
  public tl.TUser[] users;
  public tl.TChatFull full_chat;
  public tl.TChat[] chats;
}
