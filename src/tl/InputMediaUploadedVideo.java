package tl;

import java.nio.ByteBuffer;

public class InputMediaUploadedVideo extends tl.TInputMedia {
  
  public InputMediaUploadedVideo(ByteBuffer buffer) {
    file = (tl.TInputFile) TL.read(buffer);
    duration = buffer.getInt();
    w = buffer.getInt();
    h = buffer.getInt();
  }
  
  public InputMediaUploadedVideo(tl.TInputFile file, int duration, int w, int h) {
    this.file = file;
    this.duration = duration;
    this.w = w;
    this.h = h;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4847d92a);
    }
    file.writeTo(buffer, false);
    buffer.putInt(duration);
    buffer.putInt(w);
    buffer.putInt(h);
  	return buffer;
  }
  
  public int length() {
    return 16 + file.length();
  }
  
  public String toString() {
    return "(InputMediaUploadedVideo file:" + file + " duration:" + duration + " w:" + w + " h:" + h + ")";
  }
}
