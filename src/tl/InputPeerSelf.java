package tl;

import java.nio.ByteBuffer;

public class InputPeerSelf extends tl.TInputPeer {

  
  public InputPeerSelf(ByteBuffer buffer) {

  }
  
  public InputPeerSelf() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7da07ec9);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputPeerSelf)";
  }
}
