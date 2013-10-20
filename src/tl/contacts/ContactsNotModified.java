package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ContactsNotModified extends tl.contacts.TContacts {

  
  public ContactsNotModified(ByteBuffer buffer) {

  }
  
  public ContactsNotModified() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb74ba9d2);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ContactsNotModified: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(contacts.contactsNotModified)";
  }
}
