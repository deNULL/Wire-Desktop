package tl;

import java.nio.ByteBuffer;

public class RpcAnswerDroppedRunning extends tl.TRpcDropAnswer {

  
  public RpcAnswerDroppedRunning(ByteBuffer buffer) {

  }
  
  public RpcAnswerDroppedRunning() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xcd78e586);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(RpcAnswerDroppedRunning)";
  }
}
