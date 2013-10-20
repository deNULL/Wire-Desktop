package tl;

import java.nio.ByteBuffer;

public class UpdateUserStatus extends tl.TUpdate {
  
  public UpdateUserStatus(ByteBuffer buffer) {
    user_id = buffer.getInt();
    status = (tl.TUserStatus) TL.read(buffer);
  }
  
  public UpdateUserStatus(int user_id, tl.TUserStatus status) {
    this.user_id = user_id;
    this.status = status;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1bfbd823);
    }
    buffer.putInt(user_id);
    status.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + status.length();
  }
  
  public String toString() {
    return "(UpdateUserStatus user_id:" + user_id + " status:" + status + ")";
  }
}
