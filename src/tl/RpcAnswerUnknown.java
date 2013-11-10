package tl;

import java.nio.ByteBuffer;

public class RpcAnswerUnknown extends tl.TRpcDropAnswer {

  
  public RpcAnswerUnknown(ByteBuffer buffer) throws Exception {

  }
  
  public RpcAnswerUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5e2ad36e);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RpcAnswerUnknown: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(rpc_answer_unknown)";
  }
}
