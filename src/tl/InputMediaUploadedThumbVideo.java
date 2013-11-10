package tl;

import java.nio.ByteBuffer;

public class InputMediaUploadedThumbVideo extends tl.TInputMedia {

  
  public InputMediaUploadedThumbVideo(ByteBuffer buffer) throws Exception {
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe628a145);
    }
    file.writeTo(buffer, true);
    thumb.writeTo(buffer, true);
    buffer.putInt(duration);
    buffer.putInt(w);
    buffer.putInt(h);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaUploadedThumbVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + file.length() + thumb.length();
  }
  
  public String toString() {
    return "(inputMediaUploadedThumbVideo file:" + file + " thumb:" + thumb + " duration:" + duration + " w:" + w + " h:" + h + ")";
  }
}
