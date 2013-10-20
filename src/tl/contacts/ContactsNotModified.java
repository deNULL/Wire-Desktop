package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ContactsNotModified extends tl.contacts.TContacts {

  
  public ContactsNotModified(ByteBuffer buffer) {

  }
  
  public ContactsNotModified() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb74ba9d2);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ContactsNotModified)";
  }
}
