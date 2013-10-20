package tl;

import java.nio.ByteBuffer;

public class InputNotifyChats extends tl.TInputNotifyPeer {

  
  public InputNotifyChats(ByteBuffer buffer) {

  }
  
  public InputNotifyChats() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4a95e84e);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputNotifyChats)";
  }
}
