package tl;

import java.nio.ByteBuffer;

public class MessageActionEmpty extends tl.TMessageAction {

  
  public MessageActionEmpty(ByteBuffer buffer) {

  }
  
  public MessageActionEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb6aef7b0);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(messageActionEmpty)";
  }
}
