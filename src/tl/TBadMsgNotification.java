package tl;

public abstract class TBadMsgNotification extends tl.TLObject {
  public long new_server_salt;
  public int error_code;
  public long bad_msg_id;
  public int bad_msg_seqno;
}
