package tl;

import java.nio.ByteBuffer;

public class InputPhoneContact extends tl.TInputContact {
  
  public InputPhoneContact(ByteBuffer buffer) {
    client_id = buffer.getLong();
    phone = new String(TL.readString(buffer));
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public InputPhoneContact(long client_id, String phone, String first_name, String last_name) {
    this.client_id = client_id;
    this.phone = phone;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf392b7f4);
    }
    buffer.putLong(client_id);
    TL.writeString(buffer, phone.getBytes(), false);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(phone.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(InputPhoneContact client_id:" + String.format("0x%016x", client_id) + " phone:" + "phone" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
