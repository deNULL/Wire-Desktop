package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class SetTyping extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public boolean typing;
  
  public SetTyping(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    typing = (buffer.getInt() == 0x997275b5);
  }
  
  public SetTyping(tl.TInputGeoChat peer, boolean typing) {
    this.peer = peer;
    this.typing = typing;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8b8a729);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(typing ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SetTyping: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(geochats.setTyping peer:" + peer + " typing:" + (typing ? "true" : "false") + ")";
  }
}
