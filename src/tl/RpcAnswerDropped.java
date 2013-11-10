package tl;

import java.nio.ByteBuffer;

public class RpcAnswerDropped extends tl.TRpcDropAnswer {

  
  public RpcAnswerDropped(ByteBuffer buffer) throws Exception {
    msg_id = buffer.getLong();
    seq_no = buffer.getInt();
    bytes = buffer.getInt();
  }
  
  public RpcAnswerDropped(long msg_id, int seq_no, int bytes) {
    this.msg_id = msg_id;
    this.seq_no = seq_no;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa43ad8b7);
    }
    buffer.putLong(msg_id);
    buffer.putInt(seq_no);
    buffer.putInt(bytes);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RpcAnswerDropped: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(rpc_answer_dropped msg_id:" + String.format("0x%016x", msg_id) + " seq_no:" + seq_no + " bytes:" + bytes + ")";
  }
}
