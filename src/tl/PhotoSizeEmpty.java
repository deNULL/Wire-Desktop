package tl;

import java.nio.ByteBuffer;

public class PhotoSizeEmpty extends tl.TPhotoSize {

  
  public PhotoSizeEmpty(ByteBuffer buffer) throws Exception {
    type = new String(TL.readString(buffer), "UTF8");
  }
  
  public PhotoSizeEmpty(String type) {
    this.type = type;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe17e23c);
    }
    TL.writeString(buffer, type.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PhotoSizeEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(type.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(photoSizeEmpty type:" + "type" + ")";
  }
}
