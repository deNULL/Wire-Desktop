package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ForwardMessages extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int[] id;
  
  public ForwardMessages(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputPeer) TL.read(buffer);
    id = TL.readVectorInt(buffer, true);
  }
  
  public ForwardMessages(tl.TInputPeer peer, int[] id) {
    this.peer = peer;
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x514cd10f);
    }
    peer.writeTo(buffer, true);
    TL.writeVector(buffer, id, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ForwardMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + peer.length() + id.length * 4;
  }
  
  public String toString() {
    return "(messages.forwardMessages peer:" + peer + " id:" + TL.toString(id) + ")";
  }
}
