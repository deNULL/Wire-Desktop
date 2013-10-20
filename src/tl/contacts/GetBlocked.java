package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetBlocked extends tl.TLFunction {
  public int offset;
  public int limit;
  
  public GetBlocked(ByteBuffer buffer) {
    offset = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetBlocked(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf57c350f);
    }
    buffer.putInt(offset);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(GetBlocked offset:" + offset + " limit:" + limit + ")";
  }
}
