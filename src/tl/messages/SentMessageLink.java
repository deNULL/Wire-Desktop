package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SentMessageLink extends tl.messages.TSentMessage {
  
  public SentMessageLink(ByteBuffer buffer) {
    id = buffer.getInt();
    date = buffer.getInt();
    pts = buffer.getInt();
    seq = buffer.getInt();
    links = TL.readVector(buffer, true, new tl.contacts.TLink[0]);
  }
  
  public SentMessageLink(int id, int date, int pts, int seq, tl.contacts.TLink[] links) {
    this.id = id;
    this.date = date;
    this.pts = pts;
    this.seq = seq;
    this.links = links;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe9db4a3f);
    }
    buffer.putInt(id);
    buffer.putInt(date);
    buffer.putInt(pts);
    buffer.putInt(seq);
    TL.writeVector(buffer, links, true, false);
  	return buffer;
  }
  
  public int length() {
    return 24 + TL.length(links);
  }
  
  public String toString() {
    return "(SentMessageLink id:" + id + " date:" + date + " pts:" + pts + " seq:" + seq + " links:" + TL.toString(links) + ")";
  }
}
