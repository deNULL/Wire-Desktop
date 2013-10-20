package tl;

import java.nio.ByteBuffer;

public class MessageActionGeoChatCheckin extends tl.TMessageAction {

  
  public MessageActionGeoChatCheckin(ByteBuffer buffer) {

  }
  
  public MessageActionGeoChatCheckin() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc7d53de);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MessageActionGeoChatCheckin)";
  }
}
