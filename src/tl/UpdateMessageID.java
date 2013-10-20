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
    if (boxed) {
      buffer.putInt(0x4e90bfd6);
    }
    buffer.putInt(id);
    buffer.putLong(random_id);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(UpdateMessageID id:" + id + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
