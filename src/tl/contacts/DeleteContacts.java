package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteContacts extends tl.TLFunction {
  public tl.TInputUser[] id;
  
  public DeleteContacts(ByteBuffer buffer) {
    id = TL.readVector(buffer, true, new tl.TInputUser[0]);
  }
  
  public DeleteContacts(tl.TInputUser[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x59ab389e);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(id);
  }
  
  public String toString() {
    return "(DeleteContacts id:" + TL.toString(id) + ")";
  }
}
