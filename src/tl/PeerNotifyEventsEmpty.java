package tl;

import java.nio.ByteBuffer;

public class PeerNotifyEventsEmpty extends tl.TPeerNotifyEvents {

  
  public PeerNotifyEventsEmpty(ByteBuffer buffer) {

  }
  
  public PeerNotifyEventsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xadd53cb3);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(PeerNotifyEventsEmpty)";
  }
}
