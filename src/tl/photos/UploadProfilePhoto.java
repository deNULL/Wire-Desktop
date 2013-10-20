package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class UploadProfilePhoto extends tl.TLFunction {
  public tl.TInputFile file;
  public String caption;
  public tl.TInputGeoPoint geo_point;
  public tl.TInputPhotoCrop crop;
  
  public UploadProfilePhoto(ByteBuffer buffer) {
    file = (tl.TInputFile) TL.read(buffer);
    caption = new String(TL.readString(buffer));
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
    crop = (tl.TInputPhotoCrop) TL.read(buffer);
  }
  
  public UploadProfilePhoto(tl.TInputFile file, String caption, tl.TInputGeoPoint geo_point, tl.TInputPhotoCrop crop) {
    this.file = file;
    this.caption = caption;
    this.geo_point = geo_point;
    this.crop = crop;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd50f9c88);
    }
    file.writeTo(buffer, false);
    TL.writeString(buffer, caption.getBytes(), false);
    geo_point.writeTo(buffer, false);
    crop.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + file.length() + TL.length(caption.getBytes()) + geo_point.length() + crop.length();
  }
  
  public String toString() {
    return "(UploadProfilePhoto file:" + file + " caption:" + "caption" + " geo_point:" + geo_point + " crop:" + crop + ")";
  }
}
