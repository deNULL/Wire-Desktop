package tl;

import java.nio.ByteBuffer;

public class RpcAnswerDroppedRunning extends tl.TRpcDropAnswer {

  
  public RpcAnswerDroppedRunning(ByteBuffer buffer) {

  }
  
  public RpcAnswerDroppedRunning() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xcd78e586);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RpcAnswerDroppedRunning: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(rpc_answer_dropped_running)";
  }
}
