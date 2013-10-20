package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer3 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer3(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer3(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb7475268);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer3 query:" + query + ")";
  }
}
