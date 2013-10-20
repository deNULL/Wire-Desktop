package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifyEventsAll extends tl.TInputPeerNotifyEvents {

  
  public InputPeerNotifyEventsAll(ByteBuffer buffer) {

  }
  
  public InputPeerNotifyEventsAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe86a2c74);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputPeerNotifyEventsAll)";
  }
}
