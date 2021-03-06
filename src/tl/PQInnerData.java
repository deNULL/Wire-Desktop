package tl;

import java.nio.ByteBuffer;

public class PQInnerData extends tl.TPQInnerData {

  
  public PQInnerData(ByteBuffer buffer) throws Exception {
    pq = new java.math.BigInteger(1, TL.readString(buffer));
    p = new java.math.BigInteger(1, TL.readString(buffer));
    q = new java.math.BigInteger(1, TL.readString(buffer));
    nonce = TL.readInt128(buffer);
    server_nonce = TL.readInt128(buffer);
    new_nonce = TL.readInt256(buffer);
  }
  
  public PQInnerData(java.math.BigInteger pq, java.math.BigInteger p, java.math.BigInteger q, byte[] nonce, byte[] server_nonce, byte[] new_nonce) {
    this.pq = pq;
    this.p = p;
    this.q = q;
    this.nonce = nonce;
    this.server_nonce = server_nonce;
    this.new_nonce = new_nonce;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x83c95aec);
    }
    TL.writeString(buffer, pq.toByteArray(), false);
    TL.writeString(buffer, p.toByteArray(), false);
    TL.writeString(buffer, q.toByteArray(), false);
    buffer.put(nonce);
    buffer.put(server_nonce);
    buffer.put(new_nonce);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PQInnerData: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 64 + TL.length(pq.toByteArray()) + TL.length(p.toByteArray()) + TL.length(q.toByteArray());
  }
  
  public String toString() {
    return "(p_q_inner_data pq:" + "pq" + " p:\"" + p + "\" q:\"" + q + "\" nonce:" + new java.math.BigInteger(nonce) + " server_nonce:" + new java.math.BigInteger(server_nonce) + " new_nonce:" + new java.math.BigInteger(new_nonce) + ")";
  }
}
