package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Unblock extends tl.TLFunction {
  public tl.TInputUser id;
  
  public Unblock(ByteBuffer buffer) throws Exception {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public Unblock(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe54100bd);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Unblock: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(contacts.unblock id:" + id + ")";
  }
}
