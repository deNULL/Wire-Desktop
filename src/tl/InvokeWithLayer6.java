package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer6 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer6(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer6(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3a64d54d);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer6 query:" + query + ")";
  }
}
