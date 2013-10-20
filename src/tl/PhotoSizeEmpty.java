package tl;

import java.nio.ByteBuffer;

public class PhotoSizeEmpty extends tl.TPhotoSize {
  
  public PhotoSizeEmpty(ByteBuffer buffer) {
    type = new String(TL.readString(buffer));
  }
  
  public PhotoSizeEmpty(String type) {
    this.type = type;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe17e23c);
    }
    TL.writeString(buffer, type.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(type.getBytes());
  }
  
  public String toString() {
    return "(PhotoSizeEmpty type:" + "type" + ")";
  }
}
