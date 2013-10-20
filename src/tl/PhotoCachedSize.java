package tl;

import java.nio.ByteBuffer;

public class PhotoCachedSize extends tl.TPhotoSize {
  
  public PhotoCachedSize(ByteBuffer buffer) {
    type = new String(TL.readString(buffer));
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe9a734fa);
    }
    TL.writeString(buffer, type.getBytes(), false);
    location.writeTo(buffer, false);
    buffer.putInt(w);
    buffer.putInt(h);
    TL.writeString(buffer, bytes, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(type.getBytes()) + location.length() + TL.length(bytes);
  }
  
  public String toString() {
    return "(PhotoCachedSize type:" + "type" + " location:" + location + " w:" + w + " h:" + h + " bytes:" + TL.toString(bytes) + ")";
  }
}
