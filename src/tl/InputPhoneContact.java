package tl;

import java.nio.ByteBuffer;

public class InputPhoneContact extends tl.TInputContact {

  
  public InputPhoneContact(ByteBuffer buffer) throws Exception {
    client_id = buffer.getLong();
    phone = new String(TL.readString(buffer), "UTF8");
    first_name = new String(TL.readString(buffer), "UTF8");
    last_name = new String(TL.readString(buffer), "UTF8");
  }
  
  public InputPhoneContact(long client_id, String phone, String first_name, String last_name) {
    this.client_id = client_id;
    this.phone = phone;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf392b7f4);
    }
    buffer.putLong(client_id);
    TL.writeString(buffer, phone.getBytes("UTF8"), false);
    TL.writeString(buffer, first_name.getBytes("UTF8"), false);
    TL.writeString(buffer, last_name.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPhoneContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(phone.getBytes("UTF8")) + TL.length(first_name.getBytes("UTF8")) + TL.length(last_name.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputPhoneContact client_id:" + String.format("0x%016x", client_id) + " phone:" + "phone" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
