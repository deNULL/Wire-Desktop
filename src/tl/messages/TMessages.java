package tl.messages;

public abstract class TMessages extends tl.TLObject {
  public tl.TUser[] users;
  public int count;
  public tl.TMessage[] messages;
  public tl.TChat[] chats;
}
