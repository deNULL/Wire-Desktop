package tl;

import java.nio.ByteBuffer;

public class Pong extends tl.TPong {
  
  public Pong(ByteBuffer buffer) {
    msg_id = buffer.getLong();
    ping_id = buffer.getLong();
  }
  
  public Pong(long msg_id, long ping_id) {
    this.msg_id = msg_id;
    this.ping_id = ping_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x347773c5);
    }
    buffer.putLong(msg_id);
    buffer.putLong(ping_id);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(Pong msg_id:" + String.format("0x%016x", msg_id) + " ping_id:" + String.format("0x%016x", ping_id) + ")";
  }
}
