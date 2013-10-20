package tl;

import java.nio.ByteBuffer;

public class MessageActionChatDeletePhoto extends tl.TMessageAction {

  
  public MessageActionChatDeletePhoto(ByteBuffer buffer) {

  }
  
  public MessageActionChatDeletePhoto() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x95e3fbef);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatDeletePhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(messageActionChatDeletePhoto)";
  }
}
