package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ForwardMessages extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int[] id;
  
  public ForwardMessages(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    id = TL.readVectorInt(buffer, true);
  }
  
  public ForwardMessages(tl.TInputPeer peer, int[] id) {
    this.peer = peer;
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x514cd10f);
    }
    peer.writeTo(buffer, false);
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length() + id.length * 4;
  }
  
  public String toString() {
    return "(ForwardMessages peer:" + peer + " id:" + TL.toString(id) + ")";
  }
}
