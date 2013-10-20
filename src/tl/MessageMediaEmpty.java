package tl;

import java.nio.ByteBuffer;

public class MessageMediaEmpty extends tl.TMessageMedia {

  
  public MessageMediaEmpty(ByteBuffer buffer) {

  }
  
  public MessageMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3ded6320);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageMediaEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(messageMediaEmpty)";
  }
}
