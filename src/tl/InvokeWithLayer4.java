package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer4 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer4(ByteBuffer buffer) {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer4(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xdea0d430);
    }
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(InvokeWithLayer4 query:" + query + ")";
  }
}
