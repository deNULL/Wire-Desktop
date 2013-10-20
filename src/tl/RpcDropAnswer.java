package tl;

import java.nio.ByteBuffer;

public class RpcDropAnswer extends tl.TRpcDropAnswer {

  
  public RpcDropAnswer(ByteBuffer buffer) {
    req_msg_id = buffer.getLong();
  }
  
  public RpcDropAnswer(long req_msg_id) {
    this.req_msg_id = req_msg_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x58e4a740);
    }
    buffer.putLong(req_msg_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RpcDropAnswer: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(rpc_drop_answer req_msg_id:" + String.format("0x%016x", req_msg_id) + ")";
  }
}
