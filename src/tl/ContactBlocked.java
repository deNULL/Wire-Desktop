package tl;

import java.nio.ByteBuffer;

public class ContactBlocked extends tl.TContactBlocked {

  
  public ContactBlocked(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    date = buffer.getInt();
  }
  
  public ContactBlocked(int user_id, int date) {
    this.user_id = user_id;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x561bc879);
    }
    buffer.putInt(user_id);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ContactBlocked: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(contactBlocked user_id:" + user_id + " date:" + date + ")";
  }
}
