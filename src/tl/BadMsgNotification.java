package tl;

import java.nio.ByteBuffer;

public class BadMsgNotification extends tl.TBadMsgNotification {

  
  public BadMsgNotification(ByteBuffer buffer) throws Exception {
    bad_msg_id = buffer.getLong();
    bad_msg_seqno = buffer.getInt();
    error_code = buffer.getInt();
  }
  
  public BadMsgNotification(long bad_msg_id, int bad_msg_seqno, int error_code) {
    this.bad_msg_id = bad_msg_id;
    this.bad_msg_seqno = bad_msg_seqno;
    this.error_code = error_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa7eff811);
    }
    buffer.putLong(bad_msg_id);
    buffer.putInt(bad_msg_seqno);
    buffer.putInt(error_code);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BadMsgNotification: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(bad_msg_notification bad_msg_id:" + String.format("0x%016x", bad_msg_id) + " bad_msg_seqno:" + bad_msg_seqno + " error_code:" + error_code + ")";
  }
}
