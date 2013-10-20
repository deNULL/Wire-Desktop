package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer7 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer7(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer7(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa5be56d3);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer7 query:" + query + ")";
  }
}
