package tl;

import java.nio.ByteBuffer;

public class UpdateUserName extends tl.TUpdate {

  
  public UpdateUserName(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
  }
  
  public UpdateUserName(int user_id, String first_name, String last_name) {
    this.user_id = user_id;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xda22d9ad);
    }
    buffer.putInt(user_id);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateUserName: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(updateUserName user_id:" + user_id + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
