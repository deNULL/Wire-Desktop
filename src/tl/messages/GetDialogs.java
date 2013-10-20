package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDialogs extends tl.TLFunction {
  public int offset;
  public int max_id;
  public int limit;
  
  public GetDialogs(ByteBuffer buffer) {
    offset = buffer.getInt();
    max_id = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetDialogs(int offset, int max_id, int limit) {
    this.offset = offset;
    this.max_id = max_id;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xeccf1df6);
    }
    buffer.putInt(offset);
    buffer.putInt(max_id);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(GetDialogs offset:" + offset + " max_id:" + max_id + " limit:" + limit + ")";
  }
}
