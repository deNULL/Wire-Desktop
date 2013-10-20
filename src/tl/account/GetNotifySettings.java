package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class GetNotifySettings extends tl.TLFunction {
  public tl.TInputNotifyPeer peer;
  
  public GetNotifySettings(ByteBuffer buffer) {
    peer = (tl.TInputNotifyPeer) TL.read(buffer);
  }
  
  public GetNotifySettings(tl.TInputNotifyPeer peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x12b3ad31);
    }
    peer.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(GetNotifySettings peer:" + peer + ")";
  }
}
