package tl;

import java.nio.ByteBuffer;

public class ServerDHParamsFail extends tl.TServerDHParams {

  
  public ServerDHParamsFail(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce_hash = TL.readInt128(buffer);
  }
  
  public ServerDHParamsFail(byte[] nonce, byte[] server_nonce, byte[] new_nonce_hash) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce_hash = new_nonce_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x79cb045d);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce_hash);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ServerDHParamsFail: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 48;
  }
  
  public String toString() {
    return "(server_DH_params_fail nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce_hash:" + new java.math.BigInteger(new_nonce_hash) + ")";
  }
}
