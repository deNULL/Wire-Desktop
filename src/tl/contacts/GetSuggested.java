package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetSuggested extends tl.TLFunction {
  public int limit;
  
  public GetSuggested(ByteBuffer buffer) throws Exception {
    limit = buffer.getInt();
  }
  
  public GetSuggested(int limit) {
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xcd773428);
    }
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetSuggested: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(contacts.getSuggested limit:" + limit + ")";
  }
}
