package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteContact extends tl.TLFunction {
  public tl.TInputUser id;
  
  public DeleteContact(ByteBuffer buffer) {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public DeleteContact(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8e953744);
    }
    id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(DeleteContact id:" + id + ")";
  }
}
