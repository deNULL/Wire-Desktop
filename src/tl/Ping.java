package tl;

import java.nio.ByteBuffer;

public class Ping extends tl.TPong {
  
  public Ping(ByteBuffer buffer) {
    ping_id = buffer.getLong();
  }
  
  public Ping(long ping_id) {
    this.ping_id = ping_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7abe77ec);
    }
    buffer.putLong(ping_id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(Ping ping_id:" + String.format("0x%016x", ping_id) + ")";
  }
}
