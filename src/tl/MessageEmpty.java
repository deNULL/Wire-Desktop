package tl;

import java.nio.ByteBuffer;

public class MessageEmpty extends tl.TMessage {

  
  public MessageEmpty(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
  }
  
  public MessageEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x83e5de54);
    }
    buffer.putInt(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(messageEmpty id:" + id + ")";
  }
}
