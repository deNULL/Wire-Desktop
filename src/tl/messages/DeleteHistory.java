package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteHistory extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int offset;
  
  public DeleteHistory(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    offset = buffer.getInt();
  }
  
  public DeleteHistory(tl.TInputPeer peer, int offset) {
    this.peer = peer;
    this.offset = offset;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf4f8fb61);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(offset);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(DeleteHistory peer:" + peer + " offset:" + offset + ")";
  }
}
