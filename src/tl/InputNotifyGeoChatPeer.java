package tl;

import java.nio.ByteBuffer;

public class InputNotifyGeoChatPeer extends tl.TInputNotifyPeer {
  public tl.TInputPeer peer;
  
  public InputNotifyGeoChatPeer(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
  }
  
  public InputNotifyGeoChatPeer(tl.TInputPeer peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4d8ddec8);
    }
    peer.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputNotifyGeoChatPeer: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(inputNotifyGeoChatPeer peer:" + peer + ")";
  }
}
