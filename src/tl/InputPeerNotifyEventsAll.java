package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifyEventsAll extends tl.TInputPeerNotifyEvents {

  
  public InputPeerNotifyEventsAll(ByteBuffer buffer) {

  }
  
  public InputPeerNotifyEventsAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe86a2c74);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerNotifyEventsAll: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPeerNotifyEventsAll)";
  }
}
