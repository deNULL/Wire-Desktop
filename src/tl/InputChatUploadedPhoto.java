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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x94254732);
    }
    file.writeTo(buffer, true);
    crop.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputChatUploadedPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + file.length() + crop.length();
  }
  
  public String toString() {
    return "(inputChatUploadedPhoto file:" + file + " crop:" + crop + ")";
  }
}
