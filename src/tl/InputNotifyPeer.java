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
    if (boxed) {
      buffer.putInt(0xb8bc5b0c);
    }
    peer.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(InputNotifyPeer peer:" + peer + ")";
  }
}
