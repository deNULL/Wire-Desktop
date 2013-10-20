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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7abe77ec);
    }
    buffer.putLong(ping_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Ping: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(ping ping_id:" + String.format("0x%016x", ping_id) + ")";
  }
}
