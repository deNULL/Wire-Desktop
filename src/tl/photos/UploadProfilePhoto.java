package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class UploadProfilePhoto extends tl.TLFunction {
  public tl.TInputFile file;
  public String caption;
  public tl.TInputGeoPoint geo_point;
  public tl.TInputPhotoCrop crop;
  
  public UploadProfilePhoto(ByteBuffer buffer) throws Exception {
    file = (tl.TInputFile) TL.read(buffer);
    caption = new String(TL.readString(buffer), "UTF8");
    geo_point = (tl.TInputGeoPoint) TL.read(buffer);
    crop = (tl.TInputPhotoCrop) TL.read(buffer);
  }
  
  public UploadProfilePhoto(tl.TInputFile file, String caption, tl.TInputGeoPoint geo_point, tl.TInputPhotoCrop crop) {
    this.file = file;
    this.caption = caption;
    this.geo_point = geo_point;
    this.crop = crop;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd50f9c88);
    }
    file.writeTo(buffer, true);
    TL.writeString(buffer, caption.getBytes("UTF8"), false);
    geo_point.writeTo(buffer, true);
    crop.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UploadProfilePhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + file.length() + TL.length(caption.getBytes("UTF8")) + geo_point.length() + crop.length();
  }
  
  public String toString() {
    return "(photos.uploadProfilePhoto file:" + file + " caption:" + "caption" + " geo_point:" + geo_point + " crop:" + crop + ")";
  }
}
