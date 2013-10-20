package tl;

import java.nio.ByteBuffer;

public class MessageMediaContact extends tl.TMessageMedia {
  
  public MessageMediaContact(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
    user_id = buffer.getInt();
  }
  
  public MessageMediaContact(String phone_number, String first_name, String last_name, int user_id) {
    this.phone_number = phone_number;
    this.first_name = first_name;
    this.last_name = last_name;
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5e7d2f39);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
    buffer.putInt(user_id);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(phone_number.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(MessageMediaContact phone_number:" + "phone_number" + " first_name:" + "first_name" + " last_name:" + "last_name" + " user_id:" + user_id + ")";
  }
}
