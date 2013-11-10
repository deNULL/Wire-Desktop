package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class GetNotifySettings extends tl.TLFunction {
  public tl.TInputNotifyPeer peer;
  
  public GetNotifySettings(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputNotifyPeer) TL.read(buffer);
  }
  
  public GetNotifySettings(tl.TInputNotifyPeer peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x12b3ad31);
    }
    peer.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetNotifySettings: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(account.getNotifySettings peer:" + peer + ")";
  }
}
