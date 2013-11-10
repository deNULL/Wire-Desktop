package tl;

import java.nio.ByteBuffer;

public class Contact extends tl.TContact {

  
  public Contact(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    mutual = (buffer.getInt() == 0x997275b5);
  }
  
  public Contact(int user_id, boolean mutual) {
    this.user_id = user_id;
    this.mutual = mutual;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf911c994);
    }
    buffer.putInt(user_id);
    buffer.putInt(mutual ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Contact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(contact user_id:" + user_id + " mutual:" + (mutual ? "true" : "false") + ")";
  }
}
