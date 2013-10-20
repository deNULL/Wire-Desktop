package tl;

import java.nio.ByteBuffer;

public class RpcResult extends tl.TRpcResult {
  
  public RpcResult(ByteBuffer buffer) {
    req_msg_id = buffer.getLong();
    result = (tl.TLObject) TL.read(buffer);
  }
  
  public RpcResult(long req_msg_id, tl.TLObject result) {
    this.req_msg_id = req_msg_id;
    this.result = result;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf35c6d01);
    }
    buffer.putLong(req_msg_id);
    result.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + result.length();
  }
  
  public String toString() {
    return "(RpcResult req_msg_id:" + String.format("0x%016x", req_msg_id) + " result:" + result + ")";
  }
}
