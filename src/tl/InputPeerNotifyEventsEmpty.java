package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifyEventsEmpty extends tl.TInputPeerNotifyEvents {

  
  public InputPeerNotifyEventsEmpty(ByteBuffer buffer) {

  }
  
  public InputPeerNotifyEventsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf03064d8);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputPeerNotifyEventsEmpty)";
  }
}
