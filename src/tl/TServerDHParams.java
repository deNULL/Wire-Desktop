package tl;

public abstract class TServerDHParams extends tl.TLObject {
  public byte[] nonce;
  public java.math.BigInteger q;
  public long public_key_fingerprint;
  public java.math.BigInteger p;
  public byte[] encrypted_answer;
  public byte[] new_nonce_hash;
  public byte[] encrypted_data;
  public byte[] server_nonce;
}
