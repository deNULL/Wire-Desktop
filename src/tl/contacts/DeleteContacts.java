package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteContacts extends tl.TLFunction {
  public tl.TInputUser[] id;
  
  public DeleteContacts(ByteBuffer buffer) throws Exception {
    id = TL.readVector(buffer, true, new tl.TInputUser[0]);
  }
  
  public DeleteContacts(tl.TInputUser[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x59ab389e);
    }
    TL.writeVector(buffer, id, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DeleteContacts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(id);
  }
  
  public String toString() {
    return "(contacts.deleteContacts id:" + TL.toString(id) + ")";
  }
}
