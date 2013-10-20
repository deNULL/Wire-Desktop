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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb04f2510);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(max_id);
    buffer.putInt(offset);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReadHistory: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length();
  }
  
  public String toString() {
    return "(messages.readHistory peer:" + peer + " max_id:" + max_id + " offset:" + offset + ")";
  }
}
