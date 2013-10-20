package tl.messages;

public abstract class TStatedMessages extends tl.TLObject {
  public tl.TUser[] users;
  public int seq;
  public tl.contacts.TLink[] links;
  public int pts;
  public tl.TMessage[] messages;
  public tl.TChat[] chats;
}
