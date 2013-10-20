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
    if (boxed) {
      buffer.putInt(0x2575bbb9);
    }
    buffer.putInt(user_id);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(UpdateContactRegistered user_id:" + user_id + " date:" + date + ")";
  }
}
