package tl.messages;

public abstract class TMessage extends tl.TLObject {
  public tl.TMessage message;
  public tl.TUser[] users;
  public tl.TChat[] chats;
}
