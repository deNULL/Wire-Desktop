package tl;

import java.nio.ByteBuffer;

public class ClientDHInnerData extends tl.TClientDHInnerData {

  
  public ClientDHInnerData(ByteBuffer buffer) throws Exception {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    retry_id = buffer.getLong();
    g_b = new java.math.BigInteger(1, TL.readString(buffer));
  }
  
  public ClientDHInnerData(byte[] nonce, byte[] server_nonce, long retry_id, java.math.BigInteger g_b) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.retry_id = retry_id;
    this.g_b = g_b;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6643b654);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.putLong(retry_id);
    TL.writeString(buffer, g_b.toByteArray(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ClientDHInnerData: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 40 + TL.length(g_b.toByteArray());
  }
  
  public String toString() {
    return "(client_DH_inner_data nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " retry_id:" + String.format("0x%016x", retry_id) + " g_b:" + "g_b" + ")";
  }
}
