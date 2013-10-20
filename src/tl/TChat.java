package tl;

public abstract class TChat extends tl.TLObject {
  public int id;
  public String title;
  public boolean checked_in;
  public tl.TGeoPoint geo;
  public String address;
  public long access_hash;
  public boolean left;
  public String venue;
  public int date;
  public tl.TChatPhoto photo;
  public int version;
  public int participants_count;
}
