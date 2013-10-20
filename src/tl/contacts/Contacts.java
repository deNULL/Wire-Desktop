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
    if (boxed) {
      buffer.putInt(0x6f8b8cb2);
    }
    TL.writeVector(buffer, contacts, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(contacts) + TL.length(users);
  }
  
  public String toString() {
    return "(Contacts contacts:" + TL.toString(contacts) + " users:" + TL.toString(users) + ")";
  }
}
