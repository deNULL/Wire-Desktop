package tl;

import java.nio.ByteBuffer;

public class UpdateActivation extends tl.TUpdate {
  
  public UpdateActivation(ByteBuffer buffer) {
    user_id = buffer.getInt();
  }
  
  public UpdateActivation(int user_id) {
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6f690963);
    }
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UpdateActivation user_id:" + user_id + ")";
  }
}
