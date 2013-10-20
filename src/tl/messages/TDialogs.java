package tl.messages;

public abstract class TDialogs extends tl.TLObject {
  public tl.TUser[] users;
  public int count;
  public tl.TMessage[] messages;
  public tl.TDialog[] dialogs;
  public tl.TChat[] chats;
}
