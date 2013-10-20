package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class Checkin extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  
  public Checkin(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
  }
  
  public Checkin(tl.TInputGeoChat peer) {
    this.peer = peer;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x55b3e8fb);
    }
    peer.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(Checkin peer:" + peer + ")";
  }
}
