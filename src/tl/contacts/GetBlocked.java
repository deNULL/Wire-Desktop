package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetBlocked extends tl.TLFunction {
  public int offset;
  public int limit;
  
  public GetBlocked(ByteBuffer buffer) throws Exception {
    offset = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetBlocked(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf57c350f);
    }
    buffer.putInt(offset);
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetBlocked: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(contacts.getBlocked offset:" + offset + " limit:" + limit + ")";
  }
}
