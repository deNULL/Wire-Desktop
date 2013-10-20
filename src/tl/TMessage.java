package tl;

public abstract class TMessage extends tl.TLObject {
  public String message;
  public int id;
  public int fwd_from_id;
  public tl.TPeer to_id;
  public int from_id;
  public tl.TMessageAction action;
  public boolean unread;
  public int date;
  public tl.TMessageMedia media;
  public int fwd_date;
  public boolean out;
}
