package tl;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class MessageMediaVideo extends tl.TMessageMedia {
  public Image cached;
  
  public MessageMediaVideo(ByteBuffer buffer) {
    video = (tl.TVideo) TL.read(buffer);
  }
  
  public MessageMediaVideo(tl.TVideo video) {
    this.video = video;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa2d24290);
    }
    video.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaVideo: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + video.length();
  }
  
  public String toString() {
    return "(messageMediaVideo video:" + video + ")";
  }

  public Image getThumbnail() {
    if (cached == null) {
      Video video = (Video) this.video;
      if (video.thumb instanceof PhotoCachedSize) {
        try {
          cached = ImageIO.read(new ByteArrayInputStream(((PhotoCachedSize) video.thumb).bytes));
        } catch (IOException e) {
          e.printStackTrace();
        }
        //cached.setDensity(DisplayMetrics.DENSITY_LOW);
      }
    }
    return cached;
  }
}
