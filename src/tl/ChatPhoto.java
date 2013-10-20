package tl;

import java.nio.ByteBuffer;

public class ChatPhoto extends tl.TChatPhoto {
  
  public ChatPhoto(ByteBuffer buffer) {
    photo_small = (tl.TFileLocation) TL.read(buffer);
    photo_big = (tl.TFileLocation) TL.read(buffer);
  }
  
  public ChatPhoto(tl.TFileLocation photo_small, tl.TFileLocation photo_big) {
    this.photo_small = photo_small;
    this.photo_big = photo_big;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6153276a);
    }
    photo_small.writeTo(buffer, false);
    photo_big.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + photo_small.length() + photo_big.length();
  }
  
  public String toString() {
    return "(ChatPhoto photo_small:" + photo_small + " photo_big:" + photo_big + ")";
  }
}
