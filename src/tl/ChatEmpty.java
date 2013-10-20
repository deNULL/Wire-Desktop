package tl;

import java.nio.ByteBuffer;

public class ChatEmpty extends tl.TChat {

  
  public ChatEmpty(ByteBuffer buffer) {
    id = buffer.getInt();
  }
  
  public ChatEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9ba2d800);
    }
    buffer.putInt(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(chatEmpty id:" + id + ")";
  }
}
