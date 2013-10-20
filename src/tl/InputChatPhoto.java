package tl;

import java.nio.ByteBuffer;

public class InputChatPhoto extends tl.TInputChatPhoto {
  
  public InputChatPhoto(ByteBuffer buffer) {
    id = (tl.TInputPhoto) TL.read(buffer);
    crop = (tl.TInputPhotoCrop) TL.read(buffer);
  }
  
  public InputChatPhoto(tl.TInputPhoto id, tl.TInputPhotoCrop crop) {
    this.id = id;
    this.crop = crop;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb2e1bf08);
    }
    id.writeTo(buffer, false);
    crop.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length() + crop.length();
  }
  
  public String toString() {
    return "(InputChatPhoto id:" + id + " crop:" + crop + ")";
  }
}
