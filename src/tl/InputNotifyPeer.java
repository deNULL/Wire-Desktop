package tl;

import java.nio.ByteBuffer;

public class InputNotifyPeer extends tl.TInputNotifyPeer {
  public tl.TInputPeer peer;
  
  public InputNotifyPeer(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
  }
  
  public InputNotifyPeer(tl.TInputPeer peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb8bc5b0c);
    }
    peer.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputNotifyPeer: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(inputNotifyPeer peer:" + peer + ")";
  }
}
