package tl;

import java.nio.ByteBuffer;

public class InputChatUploadedPhoto extends tl.TInputChatPhoto {
  
  public InputChatUploadedPhoto(ByteBuffer buffer) {
    file = (tl.TInputFile) TL.read(buffer);
    crop = (tl.TInputPhotoCrop) TL.read(buffer);
  }
  
  public InputChatUploadedPhoto(tl.TInputFile file, tl.TInputPhotoCrop crop) {
    this.file = file;
    this.crop = crop;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x94254732);
    }
    file.writeTo(buffer, false);
    crop.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + file.length() + crop.length();
  }
  
  public String toString() {
    return "(InputChatUploadedPhoto file:" + file + " crop:" + crop + ")";
  }
}
