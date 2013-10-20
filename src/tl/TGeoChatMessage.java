package tl;

public abstract class TGeoChatMessage extends tl.TLObject {
  public String message;
  public int id;
  public int chat_id;
  public int from_id;
  public tl.TMessageAction action;
  public int date;
  public tl.TMessageMedia media;
}
