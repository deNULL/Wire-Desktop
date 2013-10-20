package tl;

import java.nio.ByteBuffer;

public class MessageMediaVideo extends tl.TMessageMedia {
  
  public MessageMediaVideo(ByteBuffer buffer) {
    video = (tl.TVideo) TL.read(buffer);
  }
  
  public MessageMediaVideo(tl.TVideo video) {
    this.video = video;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa2d24290);
    }
    video.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + video.length();
  }
  
  public String toString() {
    return "(MessageMediaVideo video:" + video + ")";
  }
}
