package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class GetFullChat extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  
  public GetFullChat(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
  }
  
  public GetFullChat(tl.TInputGeoChat peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6722dd6f);
    }
    peer.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetFullChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(geochats.getFullChat peer:" + peer + ")";
  }
}
