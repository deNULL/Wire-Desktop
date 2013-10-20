package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer8 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer8(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer8(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe9abd9fd);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer8 query:" + query + ")";
  }
}
