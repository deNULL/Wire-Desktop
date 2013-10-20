package tl;

import java.nio.ByteBuffer;

public class DhGenOk extends tl.TSetClientDHParamsAnswer {
  
  public DhGenOk(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce_hash1 = TL.readInt128(buffer);
  }
  
  public DhGenOk(byte[] nonce, byte[] server_nonce, byte[] new_nonce_hash1) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce_hash1 = new_nonce_hash1;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3bcbf734);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce_hash1);
  	return buffer;
  }
  
  public int length() {
    return 48;
  }
  
  public String toString() {
    return "(DhGenOk nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce_hash1:" + new java.math.BigInteger(new_nonce_hash1) + ")";
  }
}
