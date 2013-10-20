package tl;

public abstract class TPhoto extends tl.TLObject {
  public tl.TPhotoSize[] sizes;
  public long id;
  public tl.TGeoPoint geo;
  public long access_hash;
  public String caption;
  public int user_id;
  public int date;
}
