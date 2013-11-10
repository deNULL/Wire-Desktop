package tl;

import java.nio.ByteBuffer;

public class ContactFound extends tl.TContactFound {

  
  public ContactFound(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
  }
  
  public ContactFound(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xea879f95);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ContactFound: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(contactFound user_id:" + user_id + ")";
  }
}
