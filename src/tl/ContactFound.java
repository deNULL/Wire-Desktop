package tl;

import java.nio.ByteBuffer;

public class ContactFound extends tl.TContactFound {
  
  public ContactFound(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public ContactFound(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xea879f95);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ContactFound user_id:" + user_id + ")";
  }
}
