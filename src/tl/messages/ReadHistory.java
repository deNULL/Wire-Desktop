package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReadHistory extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int max_id;
  public int offset;
  
  public ReadHistory(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    max_id = buffer.getInt();
    offset = buffer.getInt();
  }
  
  public ReadHistory(tl.TInputPeer peer, int max_id, int offset) {
    this.peer = peer;
    this.max_id = max_id;
    this.offset = offset;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb04f2510);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(max_id);
    buffer.putInt(offset);
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length();
  }
  
  public String toString() {
    return "(ReadHistory peer:" + peer + " max_id:" + max_id + " offset:" + offset + ")";
  }
}
