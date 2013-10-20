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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb2e1bf08);
    }
    id.writeTo(buffer, true);
    crop.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputChatPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length() + crop.length();
  }
  
  public String toString() {
    return "(inputChatPhoto id:" + id + " crop:" + crop + ")";
  }
}
