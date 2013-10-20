package tl;

import java.nio.ByteBuffer;

public class MessageEmpty extends tl.TMessage {
  
  public MessageEmpty(ByteBuffer buffer) {
    id = buffer.getInt();
  }
  
  public MessageEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x83e5de54);
    }
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(MessageEmpty id:" + id + ")";
  }
}
