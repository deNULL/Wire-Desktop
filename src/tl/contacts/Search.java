package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Search extends tl.TLFunction {
  public String q;
  public int limit;
  
  public Search(ByteBuffer buffer) {
    q = new String(TL.readString(buffer));
    limit = buffer.getInt();
  }
  
  public Search(String q, int limit) {
    this.q = q;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x11f812d8);
    }
    TL.writeString(buffer, q.getBytes(), false);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(q.getBytes());
  }
  
  public String toString() {
    return "(Search q:" + "q" + " limit:" + limit + ")";
  }
}
