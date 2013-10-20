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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd1cd0a4c);
    }
    TL.writeVector(buffer, imported, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ImportedContacts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(imported) + TL.length(users);
  }
  
  public String toString() {
    return "(contacts.importedContacts imported:" + TL.toString(imported) + " users:" + TL.toString(users) + ")";
  }
}
