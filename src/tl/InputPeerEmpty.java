package tl;

import java.nio.ByteBuffer;

public class InputPeerEmpty extends tl.TInputPeer {

  
  public InputPeerEmpty(ByteBuffer buffer) {

  }
  
  public InputPeerEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7f3b18ea);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputPeerEmpty)";
  }
}
