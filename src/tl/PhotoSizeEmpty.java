package tl;

import java.nio.ByteBuffer;

public class PhotoSizeEmpty extends tl.TPhotoSize {

  
  public PhotoSizeEmpty(ByteBuffer buffer) {
    try {  type = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public PhotoSizeEmpty(String type) {
    this.type = type;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe17e23c);
    }
    try { TL.writeString(buffer, type.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PhotoSizeEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(type.getBytes());
  }
  
  public String toString() {
    return "(photoSizeEmpty type:" + "type" + ")";
  }
}
