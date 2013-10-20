package tl;

import java.nio.ByteBuffer;

public class Contact extends tl.TContact {
  
  public Contact(ByteBuffer buffer) {
    user_id = buffer.getInt();
    mutual = (buffer.getInt() == 0x997275b5);
  }
  
  public Contact(int user_id, boolean mutual) {
    this.user_id = user_id;
    this.mutual = mutual;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf911c994);
    }
    buffer.putInt(user_id);
    buffer.putInt(mutual ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(Contact user_id:" + user_id + " mutual:" + (mutual ? "true" : "false") + ")";
  }
}
