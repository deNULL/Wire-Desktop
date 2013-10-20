package tl;

public abstract class TEncryptedMessage extends tl.TLObject {
  public int chat_id;
  public byte[] bytes;
  public tl.TEncryptedFile file;
  public int date;
  public long random_id;
}
