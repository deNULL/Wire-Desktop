package tl;

import java.nio.ByteBuffer;

public class MessageMediaPhoto extends tl.TMessageMedia {
  
  public MessageMediaPhoto(ByteBuffer buffer) {
    photo = (tl.TPhoto) TL.read(buffer);
  }
  
  public MessageMediaPhoto(tl.TPhoto photo) {
    this.photo = photo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc8c45a2a);
    }
    photo.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + photo.length();
  }
  
  public String toString() {
    return "(MessageMediaPhoto photo:" + photo + ")";
  }
}
