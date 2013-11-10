package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer6 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer6(ByteBuffer buffer) throws Exception {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer6(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3a64d54d);
    }
    query.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InvokeWithLayer6: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(invokeWithLayer6 query:" + query + ")";
  }
}
