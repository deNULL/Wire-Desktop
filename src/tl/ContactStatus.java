package tl;

import java.nio.ByteBuffer;

public class ContactStatus extends tl.TContactStatus {

  
  public ContactStatus(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    expires = buffer.getInt();
  }
  
  public ContactStatus(int user_id, int expires) {
    this.user_id = user_id;
    this.expires = expires;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xaa77b873);
    }
    buffer.putInt(user_id);
    buffer.putInt(expires);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ContactStatus: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(contactStatus user_id:" + user_id + " expires:" + expires + ")";
  }
}
