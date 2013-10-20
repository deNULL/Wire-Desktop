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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc8c45a2a);
    }
    photo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + photo.length();
  }
  
  public String toString() {
    return "(messageMediaPhoto photo:" + photo + ")";
  }
}
