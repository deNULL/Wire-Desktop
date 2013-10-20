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
    if (boxed) {
      buffer.putInt(0xd0028438);
    }
    buffer.putInt(user_id);
    buffer.putLong(client_id);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(ImportedContact user_id:" + user_id + " client_id:" + String.format("0x%016x", client_id) + ")";
  }
}
