package tl;

public abstract class TTransportMessage extends tl.TLObject {
  public tl.TLObject body;
  public int seqno;
  public int bytes;
  public long msg_id;
}
