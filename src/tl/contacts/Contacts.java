package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Contacts extends tl.contacts.TContacts {

  
  public Contacts(ByteBuffer buffer) {
    contacts = TL.readVector(buffer, true, new tl.TContact[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Contacts(tl.TContact[] contacts, tl.TUser[] users) {
    this.contacts = contacts;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6f8b8cb2);
    }
    TL.writeVector(buffer, contacts, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Contacts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(contacts) + TL.length(users);
  }
  
  public String toString() {
    return "(contacts.contacts contacts:" + TL.toString(contacts) + " users:" + TL.toString(users) + ")";
  }
}
