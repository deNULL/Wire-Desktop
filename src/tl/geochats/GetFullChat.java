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
    if (boxed) {
      buffer.putInt(0x6722dd6f);
    }
    peer.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(GetFullChat peer:" + peer + ")";
  }
}
