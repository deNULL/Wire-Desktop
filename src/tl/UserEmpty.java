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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x200250ba);
    }
    buffer.putInt(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(userEmpty id:" + id + ")";
  }
}
