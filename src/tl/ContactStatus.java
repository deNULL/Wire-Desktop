package tl;

import java.nio.ByteBuffer;

public class ContactStatus extends tl.TContactStatus {
  
  public ContactStatus(ByteBuffer buffer) {
    user_id = buffer.getInt();
    expires = buffer.getInt();
  }
  
  public ContactStatus(int user_id, int expires) {
    this.user_id = user_id;
    this.expires = expires;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xaa77b873);
    }
    buffer.putInt(user_id);
    buffer.putInt(expires);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(ContactStatus user_id:" + user_id + " expires:" + expires + ")";
  }
}
