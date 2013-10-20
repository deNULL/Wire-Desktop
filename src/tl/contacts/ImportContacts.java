package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ImportContacts extends tl.TLFunction {
  public tl.TInputContact[] contacts;
  public boolean replace;
  
  public ImportContacts(ByteBuffer buffer) {
    contacts = TL.readVector(buffer, true, new tl.TInputContact[0]);
    replace = (buffer.getInt() == 0x997275b5);
  }
  
  public ImportContacts(tl.TInputContact[] contacts, boolean replace) {
    this.contacts = contacts;
    this.replace = replace;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xda30b32d);
    }
    TL.writeVector(buffer, contacts, true, true);
    buffer.putInt(replace ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ImportContacts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(contacts);
  }
  
  public String toString() {
    return "(contacts.importContacts contacts:" + TL.toString(contacts) + " replace:" + (replace ? "true" : "false") + ")";
  }
}
