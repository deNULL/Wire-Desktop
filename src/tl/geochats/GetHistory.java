package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class GetHistory extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public int offset;
  public int max_id;
  public int limit;
  
  public GetHistory(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    offset = buffer.getInt();
    max_id = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetHistory(tl.TInputGeoChat peer, int offset, int max_id, int limit) {
    this.peer = peer;
    this.offset = offset;
    this.max_id = max_id;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb53f7a68);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(offset);
    buffer.putInt(max_id);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 16 + peer.length();
  }
  
  public String toString() {
    return "(GetHistory peer:" + peer + " offset:" + offset + " max_id:" + max_id + " limit:" + limit + ")";
  }
}
