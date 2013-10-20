package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class SetTyping extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public boolean typing;
  
  public SetTyping(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    typing = (buffer.getInt() == 0x997275b5);
  }
  
  public SetTyping(tl.TInputGeoChat peer, boolean typing) {
    this.peer = peer;
    this.typing = typing;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8b8a729);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(typing ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(SetTyping peer:" + peer + " typing:" + (typing ? "true" : "false") + ")";
  }
}
