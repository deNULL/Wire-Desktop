package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SignUp extends tl.TLFunction {
  public String phone_number;
  public String phone_code_hash;
  public String phone_code;
  public String first_name;
  public String last_name;
  
  public SignUp(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
    phone_code_hash = new String(TL.readString(buffer));
    phone_code = new String(TL.readString(buffer));
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public SignUp(String phone_number, String phone_code_hash, String phone_code, String first_name, String last_name) {
    this.phone_number = phone_number;
    this.phone_code_hash = phone_code_hash;
    this.phone_code = phone_code;
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1b067634);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
    TL.writeString(buffer, phone_code_hash.getBytes(), false);
    TL.writeString(buffer, phone_code.getBytes(), false);
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SignUp: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes()) + TL.length(phone_code_hash.getBytes()) + TL.length(phone_code.getBytes()) + TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(auth.signUp phone_number:" + "phone_number" + " phone_code_hash:" + "phone_code_hash" + " phone_code:" + "phone_code" + " first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
