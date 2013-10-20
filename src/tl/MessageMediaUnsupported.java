package tl;

import java.nio.ByteBuffer;

public class MessageMediaUnsupported extends tl.TMessageMedia {

  
  public MessageMediaUnsupported(ByteBuffer buffer) {
    bytes = TL.readString(buffer);
  }
  
  public MessageMediaUnsupported(byte[] bytes) {
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x29632a36);
    }
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaUnsupported: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(bytes);
  }
  
  public String toString() {
    return "(messageMediaUnsupported bytes:" + TL.toString(bytes) + ")";
  }
}
