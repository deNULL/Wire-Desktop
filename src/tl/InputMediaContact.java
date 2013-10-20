package tl;

import java.nio.ByteBuffer;

public class InputMediaContact extends tl.TInputMedia {
  
  public InputMediaContact(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public InputMediaContact(String phone_number, String first_name, String last_name) {
    this.phone_number = phone_number;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa6e45987);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(InputMediaContact phone_number:" + "phone_number" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
