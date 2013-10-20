package tl;

import java.nio.ByteBuffer;

public class HttpWait extends tl.THttpWait {
  
  public HttpWait(ByteBuffer buffer) {
    max_delay = buffer.getInt();
    wait_after = buffer.getInt();
    max_wait = buffer.getInt();
  }
  
  public HttpWait(int max_delay, int wait_after, int max_wait) {
    this.max_delay = max_delay;
    this.wait_after = wait_after;
    this.max_wait = max_wait;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9299359f);
    }
    buffer.putInt(max_delay);
    buffer.putInt(wait_after);
    buffer.putInt(max_wait);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(HttpWait max_delay:" + max_delay + " wait_after:" + wait_after + " max_wait:" + max_wait + ")";
  }
}
