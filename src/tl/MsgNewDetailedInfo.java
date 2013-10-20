package tl;

import java.nio.ByteBuffer;

public class MsgNewDetailedInfo extends tl.TMsgDetailedInfo {
  
  public MsgNewDetailedInfo(ByteBuffer buffer) {
    answer_msg_id = buffer.getLong();
    bytes = buffer.getInt();
    status = buffer.getInt();
  }
  
  public MsgNewDetailedInfo(long answer_msg_id, int bytes, int status) {
    this.answer_msg_id = answer_msg_id;
    this.bytes = bytes;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x809db6df);
    }
    buffer.putLong(answer_msg_id);
    buffer.putInt(bytes);
    buffer.putInt(status);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(MsgNewDetailedInfo answer_msg_id:" + String.format("0x%016x", answer_msg_id) + " bytes:" + bytes + " status:" + status + ")";
  }
}
