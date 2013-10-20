package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class MessageEmpty extends tl.messages.TMessage {

  
  public MessageEmpty(ByteBuffer buffer) {

  }
  
  public MessageEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3f4e0648);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MessageEmpty)";
  }
}
