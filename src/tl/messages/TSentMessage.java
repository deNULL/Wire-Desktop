package tl.messages;

public abstract class TSentMessage extends tl.TLObject {
  public int id;
  public int seq;
  public tl.contacts.TLink[] links;
  public int date;
  public int pts;
}
