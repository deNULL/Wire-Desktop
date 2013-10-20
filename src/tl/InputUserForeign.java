package tl;

import java.nio.ByteBuffer;

public class InputUserForeign extends tl.TInputUser {
  
  public InputUserForeign(ByteBuffer buffer) {
    user_id = buffer.getInt();
    access_hash = buffer.getLong();
  }
  
  public InputUserForeign(int user_id, long access_hash) {
    this.user_id = user_id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x655e74ff);
    }
    buffer.putInt(user_id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(InputUserForeign user_id:" + user_id + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
