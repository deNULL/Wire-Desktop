package tl;

import java.nio.ByteBuffer;

public class ContactBlocked extends tl.TContactBlocked {
  
  public ContactBlocked(ByteBuffer buffer) {
    user_id = buffer.getInt();
    date = buffer.getInt();
  }
  
  public ContactBlocked(int user_id, int date) {
    this.user_id = user_id;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x561bc879);
    }
    buffer.putInt(user_id);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(ContactBlocked user_id:" + user_id + " date:" + date + ")";
  }
}
