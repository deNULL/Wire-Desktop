package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer2 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer2(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer2(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x289dd1f6);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer2 query:" + query + ")";
  }
}
