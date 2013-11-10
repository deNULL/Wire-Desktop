package tl;

import java.nio.ByteBuffer;

public class ContactSuggested extends tl.TContactSuggested {

  
  public ContactSuggested(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    mutual_contacts = buffer.getInt();
  }
  
  public ContactSuggested(int user_id, int mutual_contacts) {
    this.user_id = user_id;
    this.mutual_contacts = mutual_contacts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3de191a1);
    }
    buffer.putInt(user_id);
    buffer.putInt(mutual_contacts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ContactSuggested: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(contactSuggested user_id:" + user_id + " mutual_contacts:" + mutual_contacts + ")";
  }
}
