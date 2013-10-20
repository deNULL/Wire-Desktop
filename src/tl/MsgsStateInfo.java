package tl;

import java.nio.ByteBuffer;

public class MsgsStateInfo extends tl.TMsgsStateInfo {

  
  public MsgsStateInfo(ByteBuffer buffer) {
    req_msg_id = buffer.getLong();
    info = new String(TL.readString(buffer));
  }
  
  public MsgsStateInfo(long req_msg_id, String info) {
    this.req_msg_id = req_msg_id;
    this.info = info;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x04deb57d);
    }
    buffer.putLong(req_msg_id);
    TL.writeString(buffer, info.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgsStateInfo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(info.getBytes());
  }
  
  public String toString() {
    return "(msgs_state_info req_msg_id:" + String.format("0x%016x", req_msg_id) + " info:" + "info" + ")";
  }
}
