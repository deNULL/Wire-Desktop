package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ImportedContacts extends tl.contacts.TImportedContacts {
  
  public ImportedContacts(ByteBuffer buffer) {
    imported = TL.readVector(buffer, true, new tl.TImportedContact[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public ImportedContacts(tl.TImportedContact[] imported, tl.TUser[] users) {
    this.imported = imported;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd1cd0a4c);
    }
    TL.writeVector(buffer, imported, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(imported) + TL.length(users);
  }
  
  public String toString() {
    return "(ImportedContacts imported:" + TL.toString(imported) + " users:" + TL.toString(users) + ")";
  }
}
