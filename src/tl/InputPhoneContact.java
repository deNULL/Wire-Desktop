package tl;

import java.nio.ByteBuffer;

public class InputPhoneContact extends tl.TInputContact {

  
  public InputPhoneContact(ByteBuffer buffer) {
    client_id = buffer.getLong();
    try {  phone = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  first_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  last_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public InputPhoneContact(long client_id, String phone, String first_name, String last_name) {
    this.client_id = client_id;
    this.phone = phone;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf392b7f4);
    }
    buffer.putLong(client_id);
    try { TL.writeString(buffer, phone.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, first_name.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, last_name.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPhoneContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(phone.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(inputPhoneContact client_id:" + String.format("0x%016x", client_id) + " phone:" + "phone" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
