package tl;

import java.nio.ByteBuffer;

public class ReqDHParams extends tl.TServerDHParams {

  
  public ReqDHParams(ByteBuffer buffer) throws Exception {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    p = new java.math.BigInteger(1, TL.readString(buffer));
    q = new java.math.BigInteger(1, TL.readString(buffer));
    public_key_fingerprint = buffer.getLong();
    encrypted_data = TL.readString(buffer);
  }
  
  public ReqDHParams(byte[] nonce, byte[] server_nonce, java.math.BigInteger p, java.math.BigInteger q, long public_key_fingerprint, byte[] encrypted_data) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.p = p;
    this.q = q;
    this.public_key_fingerprint = public_key_fingerprint;
    this.encrypted_data = encrypted_data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd712e4be);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    TL.writeString(buffer, p.toByteArray(), false);
    TL.writeString(buffer, q.toByteArray(), false);
    buffer.putLong(public_key_fingerprint);
    TL.writeString(buffer, encrypted_data, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReqDHParams: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 40 + TL.length(p.toByteArray()) + TL.length(q.toByteArray()) + TL.length(encrypted_data);
  }
  
  public String toString() {
    return "(req_DH_params nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " p:\"" + p + "\" q:\"" + q + "\" public_key_fingerprint:" + String.format("0x%016x", public_key_fingerprint) + " encrypted_data:" + TL.toString(encrypted_data) + ")";
  }
}
