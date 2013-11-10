package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class MessageEmpty extends tl.messages.TMessage {

  
  public MessageEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public MessageEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3f4e0648);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(messages.messageEmpty)";
  }
}
