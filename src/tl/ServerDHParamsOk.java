package tl;

import java.nio.ByteBuffer;

public class ServerDHParamsOk extends tl.TServerDHParams {

  
  public ServerDHParamsOk(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    encrypted_answer = TL.readString(buffer);
  }
  
  public ServerDHParamsOk(byte[] nonce, byte[] server_nonce, byte[] encrypted_answer) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.encrypted_answer = encrypted_answer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd0e8075c);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    TL.writeString(buffer, encrypted_answer, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ServerDHParamsOk: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 32 + TL.length(encrypted_answer);
  }
  
  public String toString() {
    return "(server_DH_params_ok nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " encrypted_answer:" + TL.toString(encrypted_answer) + ")";
  }
}
