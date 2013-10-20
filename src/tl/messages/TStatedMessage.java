package tl.messages;

public abstract class TStatedMessage extends tl.TLObject {
  public tl.TMessage message;
  public tl.TUser[] users;
  public int seq;
  public tl.contacts.TLink[] links;
  public int pts;
  public tl.TChat[] chats;
}
