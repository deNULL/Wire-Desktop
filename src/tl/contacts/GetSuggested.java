package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetSuggested extends tl.TLFunction {
  public int limit;
  
  public GetSuggested(ByteBuffer buffer) {
    limit = buffer.getInt();
  }
  
  public GetSuggested(int limit) {
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xcd773428);
    }
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(GetSuggested limit:" + limit + ")";
  }
}
