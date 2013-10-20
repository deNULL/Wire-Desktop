package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Unblock extends tl.TLFunction {
  public tl.TInputUser id;
  
  public Unblock(ByteBuffer buffer) {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public Unblock(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe54100bd);
    }
    id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(Unblock id:" + id + ")";
  }
}
