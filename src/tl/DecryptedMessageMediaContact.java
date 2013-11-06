package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaContact extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaContact(ByteBuffer buffer) {
    try {  phone_number = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  first_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  last_name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    user_id = buffer.getInt();
  }
  
  public DecryptedMessageMediaContact(String phone_number, String first_name, String last_name, int user_id) {
    this.phone_number = phone_number;
    this.first_name = first_name;
    this.last_name = last_name;
    this.user_id = user_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x588a0a97);
    }
    try { TL.writeString(buffer, phone_number.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, first_name.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, last_name.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(user_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageMediaContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(phone_number.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(decryptedMessageMediaContact phone_number:" + "phone_number" + " first_name:" + "first_name" + " last_name:" + "last_name" + " user_id:" + user_id + ")";
  }
}
