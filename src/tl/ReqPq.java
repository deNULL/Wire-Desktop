package tl;

import java.nio.ByteBuffer;

public class ReqPq extends tl.TResPQ {
  
  public ReqPq(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
  }
  
  public ReqPq(byte[] nonce) {
    this.nonce = nonce;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x60469778);
    }
    buffer.put(nonce);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(ReqPq nonce:" + new java.math.BigInteger(nonce) + ")";
  }
}
