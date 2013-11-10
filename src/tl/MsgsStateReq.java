package tl;

import java.nio.ByteBuffer;

public class MsgsStateReq extends tl.TMsgsStateReq {

  
  public MsgsStateReq(ByteBuffer buffer) throws Exception {
    msg_ids = TL.readVectorLong(buffer, true);
  }
  
  public MsgsStateReq(long[] msg_ids) {
    this.msg_ids = msg_ids;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xda69fb52);
    }
    TL.writeVector(buffer, msg_ids, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgsStateReq: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + msg_ids.length * 8;
  }
  
  public String toString() {
    return "(msgs_state_req msg_ids:" + TL.toString(msg_ids) + ")";
  }
}
