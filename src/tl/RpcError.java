package tl;

import java.nio.ByteBuffer;

public class RpcError extends tl.TRpcError {

  
  public RpcError(ByteBuffer buffer) throws Exception {
    error_code = buffer.getInt();
    error_message = new String(TL.readString(buffer), "UTF8");
  }
  
  public RpcError(int error_code, String error_message) {
    this.error_code = error_code;
    this.error_message = error_message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2144ca19);
    }
    buffer.putInt(error_code);
    TL.writeString(buffer, error_message.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RpcError: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(error_message.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(rpc_error error_code:" + error_code + " error_message:" + "error_message" + ")";
  }
}
