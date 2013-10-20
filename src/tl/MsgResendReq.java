package tl;

import java.nio.ByteBuffer;

public class MsgResendReq extends tl.TMsgResendReq {

  
  public MsgResendReq(ByteBuffer buffer) {
    msg_ids = TL.readVectorLong(buffer, true);
  }
  
  public MsgResendReq(long[] msg_ids) {
    this.msg_ids = msg_ids;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7d861a08);
    }
    TL.writeVector(buffer, msg_ids, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgResendReq: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + msg_ids.length * 8;
  }
  
  public String toString() {
    return "(msg_resend_req msg_ids:" + TL.toString(msg_ids) + ")";
  }
}
