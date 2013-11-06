package tl;

import java.nio.ByteBuffer;

public class UpdateUserName extends tl.TUpdate {

  
  public UpdateUserName(ByteBuffer buffer) {
    user_id = buffer.getInt();
    try {  first_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  last_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public UpdateUserName(int user_id, String first_name, String last_name) {
    this.user_id = user_id;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xda22d9ad);
    }
    buffer.putInt(user_id);
    try { TL.writeString(buffer, first_name.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, last_name.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateUserName: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(updateUserName user_id:" + user_id + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
