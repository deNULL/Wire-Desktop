package tl;

public abstract class TDecryptedMessage extends tl.TLObject {
  public String message;
  public byte[] random_bytes;
  public tl.TDecryptedMessageAction action;
  public tl.TDecryptedMessageMedia media;
  public long random_id;
}
