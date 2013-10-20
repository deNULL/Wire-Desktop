package tl;

import java.nio.ByteBuffer;

public class MessageActionGeoChatCheckin extends tl.TMessageAction {

  
  public MessageActionGeoChatCheckin(ByteBuffer buffer) {

  }
  
  public MessageActionGeoChatCheckin() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc7d53de);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionGeoChatCheckin: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(messageActionGeoChatCheckin)";
  }
}
