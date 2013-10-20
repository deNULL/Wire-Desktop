package tl;

import java.nio.ByteBuffer;

public class BadServerSalt extends tl.TBadMsgNotification {

  
  public BadServerSalt(ByteBuffer buffer) {
    bad_msg_id = buffer.getLong();
    bad_msg_seqno = buffer.getInt();
    error_code = buffer.getInt();
    new_server_salt = buffer.getLong();
  }
  
  public BadServerSalt(long bad_msg_id, int bad_msg_seqno, int error_code, long new_server_salt) {
    this.bad_msg_id = bad_msg_id;
    this.bad_msg_seqno = bad_msg_seqno;
    this.error_code = error_code;
    this.new_server_salt = new_server_salt;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xedab447b);
    }
    buffer.putLong(bad_msg_id);
    buffer.putInt(bad_msg_seqno);
    buffer.putInt(error_code);
    buffer.putLong(new_server_salt);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BadServerSalt: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(bad_server_salt bad_msg_id:" + String.format("0x%016x", bad_msg_id) + " bad_msg_seqno:" + bad_msg_seqno + " error_code:" + error_code + " new_server_salt:" + String.format("0x%016x", new_server_salt) + ")";
  }
}
