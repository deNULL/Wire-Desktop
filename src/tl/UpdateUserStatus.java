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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1bfbd823);
    }
    buffer.putInt(user_id);
    status.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateUserStatus: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + status.length();
  }
  
  public String toString() {
    return "(updateUserStatus user_id:" + user_id + " status:" + status + ")";
  }
}
