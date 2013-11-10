package tl;

import java.nio.ByteBuffer;

public class PeerNotifyEventsEmpty extends tl.TPeerNotifyEvents {

  
  public PeerNotifyEventsEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public PeerNotifyEventsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xadd53cb3);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PeerNotifyEventsEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(peerNotifyEventsEmpty)";
  }
}
