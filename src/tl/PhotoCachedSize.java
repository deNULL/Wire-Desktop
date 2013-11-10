package tl;

import java.nio.ByteBuffer;

public class PhotoCachedSize extends tl.TPhotoSize {

  
  public PhotoCachedSize(ByteBuffer buffer) throws Exception {
    type = new String(TL.readString(buffer), "UTF8");
    location = (tl.TFileLocation) TL.read(buffer);
    w = buffer.getInt();
    h = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public PhotoCachedSize(String type, tl.TFileLocation location, int w, int h, byte[] bytes) {
    this.type = type;
    this.location = location;
    this.w = w;
    this.h = h;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe9a734fa);
    }
    TL.writeString(buffer, type.getBytes("UTF8"), false);
    location.writeTo(buffer, true);
    buffer.putInt(w);
    buffer.putInt(h);
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PhotoCachedSize: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(type.getBytes("UTF8")) + location.length() + TL.length(bytes);
  }
  
  public String toString() {
    return "(photoCachedSize type:" + "type" + " location:" + location + " w:" + w + " h:" + h + " bytes:" + TL.toString(bytes) + ")";
  }
}
