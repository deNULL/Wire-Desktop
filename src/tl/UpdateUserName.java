package tl;

import java.nio.ByteBuffer;

public class UpdateUserName extends tl.TUpdate {
  
  public UpdateUserName(ByteBuffer buffer) {
    user_id = buffer.getInt();
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public UpdateUserName(int user_id, String first_name, String last_name) {
    this.user_id = user_id;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xda22d9ad);
    }
    buffer.putInt(user_id);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(UpdateUserName user_id:" + user_id + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
