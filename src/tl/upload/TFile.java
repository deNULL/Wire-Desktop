package tl.upload;

public abstract class TFile extends tl.TLObject {
  public byte[] bytes;
  public int mtime;
  public tl.storage.TFileType type;
}
