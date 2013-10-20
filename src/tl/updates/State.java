package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class State extends tl.updates.TState {
  
  public State(ByteBuffer buffer) {
    pts = buffer.getInt();
    qts = buffer.getInt();
    date = buffer.getInt();
    seq = buffer.getInt();
    unread_count = buffer.getInt();
  }
  
  public State(int pts, int qts, int date, int seq, int unread_count) {
    this.pts = pts;
    this.qts = qts;
    this.date = date;
    this.seq = seq;
    this.unread_count = unread_count;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa56c2a3e);
    }
    buffer.putInt(pts);
    buffer.putInt(qts);
    buffer.putInt(date);
    buffer.putInt(seq);
    buffer.putInt(unread_count);
  	return buffer;
  }
  
  public int length() {
    return 20;
  }
  
  public String toString() {
    return "(State pts:" + pts + " qts:" + qts + " date:" + date + " seq:" + seq + " unread_count:" + unread_count + ")";
  }
}
