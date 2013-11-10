package tl;

import java.nio.ByteBuffer;

public class SetClientDHParams extends tl.TSetClientDHParamsAnswer {

  
  public SetClientDHParams(ByteBuffer buffer) throws Exception {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    encrypted_data = TL.readString(buffer);
  }
  
  public SetClientDHParams(byte[] nonce, byte[] server_nonce, byte[] encrypted_data) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.encrypted_data = encrypted_data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf5045f1f);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    TL.writeString(buffer, encrypted_data, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SetClientDHParams: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 32 + TL.length(encrypted_data);
  }
  
  public String toString() {
    return "(set_client_DH_params nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " encrypted_data:" + TL.toString(encrypted_data) + ")";
  }
}
