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
    if (boxed) {
      buffer.putInt(0x7d861a08);
    }
    TL.writeVector(buffer, msg_ids, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + msg_ids.length * 8;
  }
  
  public String toString() {
    return "(MsgResendReq msg_ids:" + TL.toString(msg_ids) + ")";
  }
}
