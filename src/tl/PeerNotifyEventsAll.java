package tl;

import java.nio.ByteBuffer;

public class PeerNotifyEventsAll extends tl.TPeerNotifyEvents {

  
  public PeerNotifyEventsAll(ByteBuffer buffer) throws Exception {

  }
  
  public PeerNotifyEventsAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6d1ded88);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PeerNotifyEventsAll: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(peerNotifyEventsAll)";
  }
}
