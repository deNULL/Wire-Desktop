package tl;

public abstract class TSetClientDHParamsAnswer extends tl.TLObject {
  public byte[] new_nonce_hash3;
  public byte[] nonce;
  public byte[] new_nonce_hash2;
  public byte[] new_nonce_hash1;
  public byte[] encrypted_data;
  public byte[] server_nonce;
}
