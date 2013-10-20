package tl;

import java.nio.ByteBuffer;

public class ImportedContact extends tl.TImportedContact {

  
  public ImportedContact(ByteBuffer buffer) {
    user_id = buffer.getInt();
    client_id = buffer.getLong();
  }
  
  public ImportedContact(int user_id, long client_id) {
    this.user_id = user_id;
    this.client_id = client_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd0028438);
    }
    buffer.putInt(user_id);
    buffer.putLong(client_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ImportedContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(importedContact user_id:" + user_id + " client_id:" + String.format("0x%016x", client_id) + ")";
  }
}
