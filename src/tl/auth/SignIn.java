package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SignIn extends tl.TLFunction {
  public String phone_number;
  public String phone_code_hash;
  public String phone_code;
  
  public SignIn(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
    phone_code_hash = new String(TL.readString(buffer));
    phone_code = new String(TL.readString(buffer));
  }
  
  public SignIn(String phone_number, String phone_code_hash, String phone_code) {
    this.phone_number = phone_number;
    this.phone_code_hash = phone_code_hash;
    this.phone_code = phone_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xbcd51581);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
    TL.writeString(buffer, phone_code_hash.getBytes(), false);
    TL.writeString(buffer, phone_code.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes()) + TL.length(phone_code_hash.getBytes()) + TL.length(phone_code.getBytes());
  }
  
  public String toString() {
    return "(SignIn phone_number:" + "phone_number" + " phone_code_hash:" + "phone_code_hash" + " phone_code:" + "phone_code" + ")";
  }
}
