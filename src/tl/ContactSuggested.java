package tl;

import java.nio.ByteBuffer;

public class ContactSuggested extends tl.TContactSuggested {
  
  public ContactSuggested(ByteBuffer buffer) {
    user_id = buffer.getInt();
    mutual_contacts = buffer.getInt();
  }
  
  public ContactSuggested(int user_id, int mutual_contacts) {
    this.user_id = user_id;
    this.mutual_contacts = mutual_contacts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3de191a1);
    }
    buffer.putInt(user_id);
    buffer.putInt(mutual_contacts);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(ContactSuggested user_id:" + user_id + " mutual_contacts:" + mutual_contacts + ")";
  }
}
