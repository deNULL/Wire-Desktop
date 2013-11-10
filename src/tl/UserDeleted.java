package tl;

import java.nio.ByteBuffer;

public class UserDeleted extends tl.TUser {

  
  public UserDeleted(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
  }
  
  public UserDeleted(int id, String first_name, String last_name) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb29ad7cc);
    }
    buffer.putInt(id);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserDeleted: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(userDeleted id:" + id + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
