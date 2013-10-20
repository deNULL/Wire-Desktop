package tl;

import java.nio.ByteBuffer;

public class RpcAnswerUnknown extends tl.TRpcDropAnswer {

  
  public RpcAnswerUnknown(ByteBuffer buffer) {

  }
  
  public RpcAnswerUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5e2ad36e);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(RpcAnswerUnknown)";
  }
}
