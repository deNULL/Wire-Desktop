package tl;

import java.nio.ByteBuffer;

public class DhGenRetry extends tl.TSetClientDHParamsAnswer {
  
  public DhGenRetry(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce_hash2 = TL.readInt128(buffer);
  }
  
  public DhGenRetry(byte[] nonce, byte[] server_nonce, byte[] new_nonce_hash2) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce_hash2 = new_nonce_hash2;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x46dc1fb9);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce_hash2);
  	return buffer;
  }
  
  public int length() {
    return 48;
  }
  
  public String toString() {
    return "(DhGenRetry nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce_hash2:" + new java.math.BigInteger(new_nonce_hash2) + ")";
  }
}
