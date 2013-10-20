package tl;

import java.nio.ByteBuffer;

public class InputPeerSelf extends tl.TInputPeer {

  
  public InputPeerSelf(ByteBuffer buffer) {

  }
  
  public InputPeerSelf() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7da07ec9);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerSelf: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPeerSelf)";
  }
}
