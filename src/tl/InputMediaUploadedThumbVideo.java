package tl;

import java.nio.ByteBuffer;

public class InputMediaUploadedThumbVideo extends tl.TInputMedia {
  
  public InputMediaUploadedThumbVideo(ByteBuffer buffer) {
    file = (tl.TInputFile) TL.read(buffer);
    thumb = (tl.TInputFile) TL.read(buffer);
    duration = buffer.getInt();
    w = buffer.getInt();
    h = buffer.getInt();
  }
  
  public InputMediaUploadedThumbVideo(tl.TInputFile file, tl.TInputFile thumb, int duration, int w, int h) {
    this.file = file;
    this.thumb = thumb;
    this.duration = duration;
    this.w = w;
    this.h = h;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe628a145);
    }
    file.writeTo(buffer, false);
    thumb.writeTo(buffer, false);
    buffer.putInt(duration);
    buffer.putInt(w);
    buffer.putInt(h);
  	return buffer;
  }
  
  public int length() {
    return 20 + file.length() + thumb.length();
  }
  
  public String toString() {
    return "(InputMediaUploadedThumbVideo file:" + file + " thumb:" + thumb + " duration:" + duration + " w:" + w + " h:" + h + ")";
  }
}
