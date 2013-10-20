package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SentMessage extends tl.messages.TSentMessage {
  
  public SentMessage(ByteBuffer buffer) {
    id = buffer.getInt();
    date = buffer.getInt();
    pts = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public SentMessage(int id, int date, int pts, int seq) {
    this.id = id;
    this.date = date;
    this.pts = pts;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd1f4d35c);
    }
    buffer.putInt(id);
    buffer.putInt(date);
    buffer.putInt(pts);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(SentMessage id:" + id + " date:" + date + " pts:" + pts + " seq:" + seq + ")";
  }
}
