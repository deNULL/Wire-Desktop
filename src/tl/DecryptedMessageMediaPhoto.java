package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaPhoto extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaPhoto(ByteBuffer buffer) throws Exception {
    thumb = TL.readString(buffer);
    thumb_w = buffer.getInt();
    thumb_h = buffer.getInt();
    w = buffer.getInt();
    h = buffer.getInt();
    size = buffer.getInt();
    key = TL.readString(buffer);
    iv = TL.readString(buffer);
  }
  
  public DecryptedMessageMediaPhoto(byte[] thumb, int thumb_w, int thumb_h, int w, int h, int size, byte[] key, byte[] iv) {
    this.thumb = thumb;
    this.thumb_w = thumb_w;
    this.thumb_h = thumb_h;
    this.w = w;
    this.h = h;
    this.size = size;
    this.key = key;
    this.iv = iv;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x32798a8c);
    }
    TL.writeString(buffer, thumb, false);
    buffer.putInt(thumb_w);
    buffer.putInt(thumb_h);
    buffer.putInt(w);
    buffer.putInt(h);
    buffer.putInt(size);
    TL.writeString(buffer, key, false);
    TL.writeString(buffer, iv, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageMediaPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + TL.length(thumb) + TL.length(key) + TL.length(iv);
  }
  
  public String toString() {
    return "(decryptedMessageMediaPhoto thumb:" + TL.toString(thumb) + " thumb_w:" + thumb_w + " thumb_h:" + thumb_h + " w:" + w + " h:" + h + " size:" + size + " key:" + TL.toString(key) + " iv:" + TL.toString(iv) + ")";
  }
}
