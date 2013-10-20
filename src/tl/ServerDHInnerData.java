package tl;

import java.nio.ByteBuffer;

public class ServerDHInnerData extends tl.TServerDHInnerData {
  
  public ServerDHInnerData(ByteBuffer buffer) {
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    g = buffer.getInt();
    dh_prime = new java.math.BigInteger(1, TL.readString(buffer));
    g_a = new java.math.BigInteger(1, TL.readString(buffer));
    server_time = buffer.getInt();
  }
  
  public ServerDHInnerData(byte[] nonce, byte[] server_nonce, int g, java.math.BigInteger dh_prime, java.math.BigInteger g_a, int server_time) {
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.g = g;
    this.dh_prime = dh_prime;
    this.g_a = g_a;
    this.server_time = server_time;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb5890dba);
    }
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.putInt(g);
    TL.writeString(buffer, dh_prime.toByteArray(), false);
    TL.writeString(buffer, g_a.toByteArray(), false);
    buffer.putInt(server_time);
  	return buffer;
  }
  
  public int length() {
    return 40 + TL.length(dh_prime.toByteArray()) + TL.length(g_a.toByteArray());
  }
  
  public String toString() {
    return "(ServerDHInnerData nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " g:" + g + " dh_prime:" + "dh_prime" + " g_a:" + "g_a" + " server_time:" + server_time + ")";
  }
}
