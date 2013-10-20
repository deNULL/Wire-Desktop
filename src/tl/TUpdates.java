package tl;

public abstract class TUpdates extends tl.TLObject {
  public tl.TUpdate update;
  public int chat_id;
  public String message;
  public int id;
  public tl.TUser[] users;
  public int seq_start;
  public tl.TUpdate[] updates;
  public int from_id;
  public int seq;
  public int date;
  public int pts;
  public tl.TChat[] chats;
}
