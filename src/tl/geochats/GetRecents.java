package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class GetRecents extends tl.TLFunction {
  public int offset;
  public int limit;
  
  public GetRecents(ByteBuffer buffer) {
    offset = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetRecents(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe1427e6f);
    }
    buffer.putInt(offset);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(GetRecents offset:" + offset + " limit:" + limit + ")";
  }
}
