package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class UpdateNotifySettings extends tl.TLFunction {
  public tl.TInputNotifyPeer peer;
  public tl.TInputPeerNotifySettings settings;
  
  public UpdateNotifySettings(ByteBuffer buffer) {
    peer = (tl.TInputNotifyPeer) TL.read(buffer);
    settings = (tl.TInputPeerNotifySettings) TL.read(buffer);
  }
  
  public UpdateNotifySettings(tl.TInputNotifyPeer peer, tl.TInputPeerNotifySettings settings) {
    this.peer = peer;
    this.settings = settings;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x84be5b93);
    }
    peer.writeTo(buffer, false);
    settings.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length() + settings.length();
  }
  
  public String toString() {
    return "(UpdateNotifySettings peer:" + peer + " settings:" + settings + ")";
  }
}
