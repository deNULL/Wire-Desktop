package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteHistory extends tl.TLFunction {
  public tl.TInputPeer peer;
  public int offset;
  
  public DeleteHistory(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputPeer) TL.read(buffer);
    offset = buffer.getInt();
  }
  
  public DeleteHistory(tl.TInputPeer peer, int offset) {
    this.peer = peer;
    this.offset = offset;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf4f8fb61);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(offset);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DeleteHistory: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(messages.deleteHistory peer:" + peer + " offset:" + offset + ")";
  }
}
