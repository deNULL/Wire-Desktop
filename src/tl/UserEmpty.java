package tl;

import java.nio.ByteBuffer;

public class UserEmpty extends tl.TUser {
  
  public UserEmpty(ByteBuffer buffer) {
    id = buffer.getInt();
  }
  
  public UserEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x200250ba);
    }
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(UserEmpty id:" + id + ")";
  }
}
