package tl;

import java.nio.ByteBuffer;

public class ChatPhoto extends tl.TChatPhoto {

  
  public ChatPhoto(ByteBuffer buffer) throws Exception {
    photo_small = (tl.TFileLocation) TL.read(buffer);
    photo_big = (tl.TFileLocation) TL.read(buffer);
  }
  
  public ChatPhoto(tl.TFileLocation photo_small, tl.TFileLocation photo_big) {
    this.photo_small = photo_small;
    this.photo_big = photo_big;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6153276a);
    }
    photo_small.writeTo(buffer, true);
    photo_big.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + photo_small.length() + photo_big.length();
  }
  
  public String toString() {
    return "(chatPhoto photo_small:" + photo_small + " photo_big:" + photo_big + ")";
  }
}
