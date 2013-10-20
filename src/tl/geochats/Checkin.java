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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x55b3e8fb);
    }
    peer.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Checkin: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length();
  }
  
  public String toString() {
    return "(geochats.checkin peer:" + peer + ")";
  }
}
