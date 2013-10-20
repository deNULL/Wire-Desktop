package tl;

import java.nio.ByteBuffer;

public class MsgsAllInfo extends tl.TMsgsAllInfo {

  
  public MsgsAllInfo(ByteBuffer buffer) {
    msg_ids = TL.readVectorLong(buffer, true);
    info = new String(TL.readString(buffer));
  }
  
  public MsgsAllInfo(long[] msg_ids, String info) {
    this.msg_ids = msg_ids;
    this.info = info;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8cc0d131);
    }
    TL.writeVector(buffer, msg_ids, true, false);
    TL.writeString(buffer, info.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgsAllInfo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + msg_ids.length * 8 + TL.length(info.getBytes());
  }
  
  public String toString() {
    return "(msgs_all_info msg_ids:" + TL.toString(msg_ids) + " info:" + "info" + ")";
  }
}
