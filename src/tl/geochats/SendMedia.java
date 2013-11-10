package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class SendMedia extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public tl.TInputMedia media;
  public long random_id;
  
  public SendMedia(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    media = (tl.TInputMedia) TL.read(buffer);
    random_id = buffer.getLong();
  }
  
  public SendMedia(tl.TInputGeoChat peer, tl.TInputMedia media, long random_id) {
    this.peer = peer;
    this.media = media;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb8f0deff);
    }
    peer.writeTo(buffer, true);
    media.writeTo(buffer, true);
    buffer.putLong(random_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendMedia: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + peer.length() + media.length();
  }
  
  public String toString() {
    return "(geochats.sendMedia peer:" + peer + " media:" + media + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
