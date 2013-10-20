package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Search extends tl.TLFunction {
  public tl.TInputPeer peer;
  public String q;
  public tl.TMessagesFilter filter;
  public int min_date;
  public int max_date;
  public int offset;
  public int max_id;
  public int limit;
  
  public Search(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    q = new String(TL.readString(buffer));
    filter = (tl.TMessagesFilter) TL.read(buffer);
    min_date = buffer.getInt();
    max_date = buffer.getInt();
    offset = buffer.getInt();
    max_id = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public Search(tl.TInputPeer peer, String q, tl.TMessagesFilter filter, int min_date, int max_date, int offset, int max_id, int limit) {
    this.peer = peer;
    this.q = q;
    this.filter = filter;
    this.min_date = min_date;
    this.max_date = max_date;
    this.offset = offset;
    this.max_id = max_id;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7e9f2ab);
    }
    peer.writeTo(buffer, false);
    TL.writeString(buffer, q.getBytes(), false);
    filter.writeTo(buffer, false);
    buffer.putInt(min_date);
    buffer.putInt(max_date);
    buffer.putInt(offset);
    buffer.putInt(max_id);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 28 + peer.length() + TL.length(q.getBytes()) + filter.length();
  }
  
  public String toString() {
    return "(Search peer:" + peer + " q:" + "q" + " filter:" + filter + " min_date:" + min_date + " max_date:" + max_date + " offset:" + offset + " max_id:" + max_id + " limit:" + limit + ")";
  }
}
