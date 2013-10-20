package tl;

import java.nio.ByteBuffer;

public class BadMsgNotification extends tl.TBadMsgNotification {
  
  public BadMsgNotification(ByteBuffer buffer) {
    bad_msg_id = buffer.getLong();
    bad_msg_seqno = buffer.getInt();
    error_code = buffer.getInt();
  }
  
  public BadMsgNotification(long bad_msg_id, int bad_msg_seqno, int error_code) {
    this.bad_msg_id = bad_msg_id;
    this.bad_msg_seqno = bad_msg_seqno;
    this.error_code = error_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa7eff811);
    }
    buffer.putLong(bad_msg_id);
    buffer.putInt(bad_msg_seqno);
    buffer.putInt(error_code);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(BadMsgNotification bad_msg_id:" + String.format("0x%016x", bad_msg_id) + " bad_msg_seqno:" + bad_msg_seqno + " error_code:" + error_code + ")";
  }
}
