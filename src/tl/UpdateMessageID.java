package tl;

import java.nio.ByteBuffer;

public class UpdateMessageID extends tl.TUpdate {

  
  public UpdateMessageID(ByteBuffer buffer) {
    id = buffer.getInt();
    random_id = buffer.getLong();
  }
  
  public UpdateMessageID(int id, long random_id) {
    this.id = id;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4e90bfd6);
    }
    buffer.putInt(id);
    buffer.putLong(random_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateMessageID: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(updateMessageID id:" + id + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
