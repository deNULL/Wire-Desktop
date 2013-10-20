package tl;

import java.nio.ByteBuffer;

public class RpcError extends tl.TRpcError {
  
  public RpcError(ByteBuffer buffer) {
    error_code = buffer.getInt();
    error_message = new String(TL.readString(buffer));
  }
  
  public RpcError(int error_code, String error_message) {
    this.error_code = error_code;
    this.error_message = error_message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2144ca19);
    }
    buffer.putInt(error_code);
    TL.writeString(buffer, error_message.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(error_message.getBytes());
  }
  
  public String toString() {
    return "(RpcError error_code:" + error_code + " error_message:" + "error_message" + ")";
  }
}
