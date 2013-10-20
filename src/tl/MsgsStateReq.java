package tl;

import java.nio.ByteBuffer;

public class MsgsStateReq extends tl.TMsgsStateReq {
  
  public MsgsStateReq(ByteBuffer buffer) {
    msg_ids = TL.readVectorLong(buffer, true);
  }
  
  public MsgsStateReq(long[] msg_ids) {
    this.msg_ids = msg_ids;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xda69fb52);
    }
    TL.writeVector(buffer, msg_ids, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + msg_ids.length * 8;
  }
  
  public String toString() {
    return "(MsgsStateReq msg_ids:" + TL.toString(msg_ids) + ")";
  }
}
