package tl;

import java.nio.ByteBuffer;

public class InputMediaContact extends tl.TInputMedia {

  
  public InputMediaContact(ByteBuffer buffer) throws Exception {
    phone_number = new String(TL.readString(buffer), "UTF8");
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
  }
  
  public InputMediaContact(String phone_number, String first_name, String last_name) {
    this.phone_number = phone_number;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa6e45987);
    }
    TL.writeString(buffer, phone_number.getBytes("UTF8"), false);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputMediaContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(phone_number.getBytes("UTF8")) + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputMediaContact phone_number:" + "phone_number" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
