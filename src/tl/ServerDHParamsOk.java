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
    if (boxed) {
      buffer.putInt(0xd0e8075c);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    TL.writeString(buffer, encrypted_answer, false);
  	return buffer;
  }
  
  public int length() {
    return 32 + TL.length(encrypted_answer);
  }
  
  public String toString() {
    return "(ServerDHParamsOk nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " encrypted_answer:" + TL.toString(encrypted_answer) + ")";
  }
}
