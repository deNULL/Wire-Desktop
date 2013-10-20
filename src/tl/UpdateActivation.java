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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6f690963);
    }
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateActivation: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(updateActivation user_id:" + user_id + ")";
  }
}
