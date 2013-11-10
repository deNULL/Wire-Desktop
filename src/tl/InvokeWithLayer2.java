package tl;

import java.nio.ByteBuffer;

public class InvokeWithLayer2 extends tl.TLFunction {
  public tl.TLObject query;
  
  public InvokeWithLayer2(ByteBuffer buffer) throws Exception {
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeWithLayer2(tl.TLObject query) {
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x289dd1f6);
    }
    query.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InvokeWithLayer2: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + query.length();
  }
  
  public String toString() {
    return "(invokeWithLayer2 query:" + query + ")";
  }
}
