package tl;

import java.nio.ByteBuffer;

public class MessageActionEmpty extends tl.TMessageAction {

  
  public MessageActionEmpty(ByteBuffer buffer) {

  }
  
  public MessageActionEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb6aef7b0);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MessageActionEmpty)";
  }
}
