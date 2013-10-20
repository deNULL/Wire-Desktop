package tl;

public abstract class TEncryptedFile extends tl.TLObject {
  public long id;
  public long access_hash;
  public int dc_id;
  public int key_fingerprint;
  public int size;
}
