package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer1 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer1(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer1(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x53835315);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer1 query:" + query + ")";
  }
}
