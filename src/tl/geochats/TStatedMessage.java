package tl.geochats;

public abstract class TStatedMessage extends tl.TLObject {
  public tl.TGeoChatMessage message;
  public tl.TUser[] users;
  public int seq;
  public tl.TChat[] chats;
}
