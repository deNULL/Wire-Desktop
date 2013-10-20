package tl;

import java.nio.ByteBuffer;

public class InputPeerEmpty extends tl.TInputPeer {

  
  public InputPeerEmpty(ByteBuffer buffer) {

  }
  
  public InputPeerEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7f3b18ea);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputPeerEmpty)";
  }
}
