package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ForwardMessage extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int id;
  public long random_id;
  
  public ForwardMessage(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    id = buffer.getInt();
    random_id = buffer.getLong();
  }
  
  public ForwardMessage(tl.TInputPeer peer, int id, long random_id) {
    this.peer = peer;
    this.id = id;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3f3f4f2);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(id);
    buffer.putLong(random_id);
  	return buffer;
  }
  
  public int length() {
    return 16 + peer.length();
  }
  
  public String toString() {
    return "(ForwardMessage peer:" + peer + " id:" + id + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
