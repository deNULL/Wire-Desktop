package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifyEventsEmpty extends tl.TInputPeerNotifyEvents {

  
  public InputPeerNotifyEventsEmpty(ByteBuffer buffer) {

  }
  
  public InputPeerNotifyEventsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf03064d8);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerNotifyEventsEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPeerNotifyEventsEmpty)";
  }
}
