package tl;

import java.nio.ByteBuffer;

public class MessageActionChatDeletePhoto extends tl.TMessageAction {

  
  public MessageActionChatDeletePhoto(ByteBuffer buffer) {

  }
  
  public MessageActionChatDeletePhoto() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x95e3fbef);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MessageActionChatDeletePhoto)";
  }
}
