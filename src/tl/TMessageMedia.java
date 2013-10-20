package tl;

public abstract class TMessageMedia extends tl.TLObject {
  public String first_name;
  public byte[] bytes;
  public String phone_number;
  public tl.TGeoPoint geo;
  public String last_name;
  public int user_id;
  public tl.TPhoto photo;
  public tl.TVideo video;
}
