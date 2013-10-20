package tl;

public abstract class TClientDHInnerData extends tl.TLObject {
  public byte[] nonce;
  public java.math.BigInteger g_b;
  public long retry_id;
  public byte[] server_nonce;
}
