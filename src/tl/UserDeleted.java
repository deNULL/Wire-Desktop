package tl;

import java.nio.ByteBuffer;

public class UserDeleted extends tl.TUser {
  
  public UserDeleted(ByteBuffer buffer) {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public UserDeleted(int id, String first_name, String last_name) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb29ad7cc);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(UserDeleted id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
