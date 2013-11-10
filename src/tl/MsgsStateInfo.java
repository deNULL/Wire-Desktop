package tl;

import java.nio.ByteBuffer;

public class MsgsStateInfo extends tl.TMsgsStateInfo {

  
  public MsgsStateInfo(ByteBuffer buffer) throws Exception {
    req_msg_id = buffer.getLong();
    info = new String(TL.readString(buffer), "UTF8");
  }
  
  public MsgsStateInfo(long req_msg_id, String info) {
    this.req_msg_id = req_msg_id;
    this.info = info;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x04deb57d);
    }
    buffer.putLong(req_msg_id);
    TL.writeString(buffer, info.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgsStateInfo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(info.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(msgs_state_info req_msg_id:" + String.format("0x%016x", req_msg_id) + " info:" + "info" + ")";
  }
}
