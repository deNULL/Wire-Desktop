package tl;

import java.nio.ByteBuffer;

public class DhGenFail extends tl.TSetClientDHParamsAnswer {

  
  public DhGenFail(ByteBuffer buffer) throws Exception {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce_hash3 = TL.readInt128(buffer);
  }
  
  public DhGenFail(byte[] nonce, byte[] server_nonce, byte[] new_nonce_hash3) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce_hash3 = new_nonce_hash3;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa69dae02);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce_hash3);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DhGenFail: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 48;
  }
  
  public String toString() {
    return "(dh_gen_fail nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce_hash3:" + new java.math.BigInteger(new_nonce_hash3) + ")";
  }
}
