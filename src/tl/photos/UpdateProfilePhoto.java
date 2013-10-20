package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class UpdateProfilePhoto extends tl.TLFunction {
  public tl.TInputPhoto id;
  public tl.TInputPhotoCrop crop;
  
  public UpdateProfilePhoto(ByteBuffer buffer) {
    id = (tl.TInputPhoto) TL.read(buffer);
    crop = (tl.TInputPhotoCrop) TL.read(buffer);
  }
  
  public UpdateProfilePhoto(tl.TInputPhoto id, tl.TInputPhotoCrop crop) {
    this.id = id;
    this.crop = crop;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xeef579a0);
    }
    id.writeTo(buffer, false);
    crop.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length() + crop.length();
  }
  
  public String toString() {
    return "(UpdateProfilePhoto id:" + id + " crop:" + crop + ")";
  }
}
