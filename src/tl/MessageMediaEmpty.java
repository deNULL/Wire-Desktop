package tl;

import java.nio.ByteBuffer;

public class MessageMediaEmpty extends tl.TMessageMedia {

  
  public MessageMediaEmpty(ByteBuffer buffer) {

  }
  
  public MessageMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3ded6320);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MessageMediaEmpty)";
  }
}
