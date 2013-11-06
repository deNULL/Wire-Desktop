package tl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class MessageMediaPhoto extends tl.TMessageMedia {
  public Image cached;
  
  public MessageMediaPhoto(ByteBuffer buffer) {
    photo = (tl.TPhoto) TL.read(buffer);
  }
  
  public MessageMediaPhoto(tl.TPhoto photo) {
    this.photo = photo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc8c45a2a);
    }
    photo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + photo.length();
  }
  
  public String toString() {
    return "(messageMediaPhoto photo:" + photo + ")";
  }

  public Image getThumbnail() {
    if (cached == null) {
      Photo photo = (Photo) this.photo;
      int w = 1;
      int h = 1;
      for (TPhotoSize size : photo.sizes) {
        if (size instanceof PhotoCachedSize) {
          try {
            cached = ImageIO.read(new ByteArrayInputStream(((PhotoCachedSize) size).bytes));
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        } else if (size instanceof PhotoSize) {
          w = ((PhotoSize) size).w;
          h = ((PhotoSize) size).h;
        }
      }
      if (cached == null) {
        cached = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      }
    }
    return cached;
  }
}
