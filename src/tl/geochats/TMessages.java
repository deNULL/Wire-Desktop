package tl.geochats;

public abstract class TMessages extends tl.TLObject {
  public tl.TUser[] users;
  public int count;
  public tl.TGeoChatMessage[] messages;
  public tl.TChat[] chats;
}
