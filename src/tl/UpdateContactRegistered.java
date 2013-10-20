package tl;

import java.nio.ByteBuffer;

public class UpdateContactRegistered extends tl.TUpdate {

  
  public UpdateContactRegistered(ByteBuffer buffer) {
    user_id = buffer.getInt();
    date = buffer.getInt();
  }
  
  public UpdateContactRegistered(int user_id, int date) {
    this.user_id = user_id;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2575bbb9);
    }
    buffer.putInt(user_id);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateContactRegistered: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(updateContactRegistered user_id:" + user_id + " date:" + date + ")";
  }
}
