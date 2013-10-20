package tl;

public abstract class TEncryptedChat extends tl.TLObject {
  public int id;
  public byte[] g_a_or_b;
  public int participant_id;
  public byte[] nonce;
  public byte[] g_a;
  public long access_hash;
  public int admin_id;
  public long key_fingerprint;
  public int date;
}
