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
    if (boxed) {
      buffer.putInt(0x29632a36);
    }
    TL.writeString(buffer, bytes, false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(bytes);
  }
  
  public String toString() {
    return "(MessageMediaUnsupported bytes:" + TL.toString(bytes) + ")";
  }
}
