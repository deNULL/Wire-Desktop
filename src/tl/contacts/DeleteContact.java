package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteContact extends tl.TLFunction {
  public tl.TInputUser id;
  
  public DeleteContact(ByteBuffer buffer) throws Exception {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public DeleteContact(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8e953744);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DeleteContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(contacts.deleteContact id:" + id + ")";
  }
}
