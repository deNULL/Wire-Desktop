package tl;

public abstract class TInputEncryptedFile extends tl.TLObject {
  public long id;
  public String md5_checksum;
  public long access_hash;
  public int parts;
  public int key_fingerprint;
}
