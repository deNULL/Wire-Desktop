package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendMedia extends tl.TLFunction {
  public tl.TInputPeer peer;
  public tl.TInputMedia media;
  public long random_id;
  
  public SendMedia(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    media = (tl.TInputMedia) TL.read(buffer);
    random_id = buffer.getLong();
  }
  
  public SendMedia(tl.TInputPeer peer, tl.TInputMedia media, long random_id) {
    this.peer = peer;
    this.media = media;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa3c85d76);
    }
    peer.writeTo(buffer, false);
    media.writeTo(buffer, false);
    buffer.putLong(random_id);
  	return buffer;
  }
  
  public int length() {
    return 16 + peer.length() + media.length();
  }
  
  public String toString() {
    return "(SendMedia peer:" + peer + " media:" + media + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
