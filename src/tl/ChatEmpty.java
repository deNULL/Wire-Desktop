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
    if (boxed) {
      buffer.putInt(0x9ba2d800);
    }
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ChatEmpty id:" + id + ")";
  }
}
