package tl;

import java.nio.ByteBuffer;

public class ReqPq extends tl.TResPQ {

  
  public ReqPq(ByteBuffer buffer) throws Exception {
    nonce = TL.readInt128(buffer);
  }
  
  public ReqPq(byte[] nonce) {
    this.nonce = nonce;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x60469778);
    }
    buffer.put(nonce);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReqPq: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(req_pq nonce:" + new java.math.BigInteger(nonce) + ")";
  }
}
