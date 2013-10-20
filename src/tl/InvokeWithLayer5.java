package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer5 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer5(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer5(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x417a57ae);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer5 query:" + query + ")";
  }
}
