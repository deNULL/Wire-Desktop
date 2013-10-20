package tl;

import java.nio.ByteBuffer;

public class SetClientDHParams extends tl.TSetClientDHParamsAnswer {
  
  public SetClientDHParams(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    encrypted_data = TL.readString(buffer);
  }
  
  public SetClientDHParams(byte[] nonce, byte[] server_nonce, byte[] encrypted_data) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.encrypted_data = encrypted_data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf5045f1f);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    TL.writeString(buffer, encrypted_data, false);
  	return buffer;
  }
  
  public int length() {
    return 32 + TL.length(encrypted_data);
  }
  
  public String toString() {
    return "(SetClientDHParams nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " encrypted_data:" + TL.toString(encrypted_data) + ")";
  }
}
