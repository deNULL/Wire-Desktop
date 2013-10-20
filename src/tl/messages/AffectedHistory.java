package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class AffectedHistory extends tl.messages.TAffectedHistory {
  
  public AffectedHistory(ByteBuffer buffer) {
    pts = buffer.getInt();
    seq = buffer.getInt();
    offset = buffer.getInt();
  }
  
  public AffectedHistory(int pts, int seq, int offset) {
    this.pts = pts;
    this.seq = seq;
    this.offset = offset;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb7de36f2);
    }
    buffer.putInt(pts);
    buffer.putInt(seq);
    buffer.putInt(offset);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(AffectedHistory pts:" + pts + " seq:" + seq + " offset:" + offset + ")";
  }
}
