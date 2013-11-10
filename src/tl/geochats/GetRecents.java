package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class GetRecents extends tl.TLFunction {
  public int offset;
  public int limit;
  
  public GetRecents(ByteBuffer buffer) throws Exception {
    offset = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetRecents(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe1427e6f);
    }
    buffer.putInt(offset);
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetRecents: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(geochats.getRecents offset:" + offset + " limit:" + limit + ")";
  }
}
