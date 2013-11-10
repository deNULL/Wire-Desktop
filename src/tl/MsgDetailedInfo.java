package tl;

import java.nio.ByteBuffer;

public class MsgDetailedInfo extends tl.TMsgDetailedInfo {

  
  public MsgDetailedInfo(ByteBuffer buffer) throws Exception {
    msg_id = buffer.getLong();
    answer_msg_id = buffer.getLong();
    bytes = buffer.getInt();
    status = buffer.getInt();
  }
  
  public MsgDetailedInfo(long msg_id, long answer_msg_id, int bytes, int status) {
    this.msg_id = msg_id;
    this.answer_msg_id = answer_msg_id;
    this.bytes = bytes;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x276d3ec6);
    }
    buffer.putLong(msg_id);
    buffer.putLong(answer_msg_id);
    buffer.putInt(bytes);
    buffer.putInt(status);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgDetailedInfo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 24;
  }
  
  public String toString() {
    return "(msg_detailed_info msg_id:" + String.format("0x%016x", msg_id) + " answer_msg_id:" + String.format("0x%016x", answer_msg_id) + " bytes:" + bytes + " status:" + status + ")";
  }
}
