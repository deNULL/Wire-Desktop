package tl;

import java.nio.ByteBuffer;

public class DhGenFail extends tl.TSetClientDHParamsAnswer {
  
  public DhGenFail(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce_hash3 = TL.readInt128(buffer);
  }
  
  public DhGenFail(byte[] nonce, byte[] server_nonce, byte[] new_nonce_hash3) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce_hash3 = new_nonce_hash3;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa69dae02);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce_hash3);
  	return buffer;
  }
  
  public int length() {
    return 48;
  }
  
  public String toString() {
    return "(DhGenFail nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce_hash3:" + new java.math.BigInteger(new_nonce_hash3) + ")";
  }
}
