package tl;

import java.nio.ByteBuffer;

public class PhotoSize extends tl.TPhotoSize {

  
  public PhotoSize(ByteBuffer buffer) throws Exception {
    type = new String(TL.readString(buffer), "UTF8");
    location = (tl.TFileLocation) TL.read(buffer);
    w = buffer.getInt();
    h = buffer.getInt();
    size = buffer.getInt();
  }
  
  public PhotoSize(String type, tl.TFileLocation location, int w, int h, int size) {
    this.type = type;
    this.location = location;
    this.w = w;
    this.h = h;
    this.size = size;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x77bfb61b);
    }
    TL.writeString(buffer, type.getBytes("UTF8"), false);
    location.writeTo(buffer, true);
    buffer.putInt(w);
    buffer.putInt(h);
    buffer.putInt(size);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PhotoSize: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(type.getBytes("UTF8")) + location.length();
  }
  
  public String toString() {
    return "(photoSize type:" + "type" + " location:" + location + " w:" + w + " h:" + h + " size:" + size + ")";
  }
}
