package tl;

import java.nio.ByteBuffer;

public class InputNotifyGeoChatPeer extends tl.TInputNotifyPeer {
  public tl.TInputGeoChat peer;
  
  public InputNotifyGeoChatPeer(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
  }
  
  public InputNotifyGeoChatPeer(tl.TInputGeoChat peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4d8ddec8);
    }
    peer.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(InputNotifyGeoChatPeer peer:" + peer + ")";
  }
}
