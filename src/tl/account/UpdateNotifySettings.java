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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x84be5b93);
    }
    peer.writeTo(buffer, true);
    settings.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNotifySettings: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length() + settings.length();
  }
  
  public String toString() {
    return "(account.updateNotifySettings peer:" + peer + " settings:" + settings + ")";
  }
}
