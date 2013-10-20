package tl;

import java.nio.ByteBuffer;

public class PeerNotifyEventsAll extends tl.TPeerNotifyEvents {

  
  public PeerNotifyEventsAll(ByteBuffer buffer) {

  }
  
  public PeerNotifyEventsAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6d1ded88);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(PeerNotifyEventsAll)";
  }
}
