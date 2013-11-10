package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class AffectedHistory extends tl.messages.TAffectedHistory {

  
  public AffectedHistory(ByteBuffer buffer) throws Exception {
    pts = buffer.getInt();
    seq = buffer.getInt();
    offset = buffer.getInt();
  }
  
  public AffectedHistory(int pts, int seq, int offset) {
    this.pts = pts;
    this.seq = seq;
    this.offset = offset;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb7de36f2);
    }
    buffer.putInt(pts);
    buffer.putInt(seq);
    buffer.putInt(offset);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at AffectedHistory: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12;
  }
  
  public String toString() {
    return "(messages.affectedHistory pts:" + pts + " seq:" + seq + " offset:" + offset + ")";
  }
}
