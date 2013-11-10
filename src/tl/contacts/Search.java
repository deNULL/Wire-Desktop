package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Search extends tl.TLFunction {
  public String q;
  public int limit;
  
  public Search(ByteBuffer buffer) throws Exception {
    q = new String(TL.readString(buffer), "UTF8");
    limit = buffer.getInt();
  }
  
  public Search(String q, int limit) {
    this.q = q;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x11f812d8);
    }
    TL.writeString(buffer, q.getBytes("UTF8"), false);
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Search: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(q.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(contacts.search q:" + "q" + " limit:" + limit + ")";
  }
}
