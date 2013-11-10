package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SignIn extends tl.TLFunction {
  public String phone_number;
  public String phone_code_hash;
  public String phone_code;
  
  public SignIn(ByteBuffer buffer) throws Exception {
    phone_number = new String(TL.readString(buffer), "UTF8");
    phone_code_hash = new String(TL.readString(buffer), "UTF8");
    phone_code = new String(TL.readString(buffer), "UTF8");
  }
  
  public SignIn(String phone_number, String phone_code_hash, String phone_code) {
    this.phone_number = phone_number;
    this.phone_code_hash = phone_code_hash;
    this.phone_code = phone_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xbcd51581);
    }
    TL.writeString(buffer, phone_number.getBytes("UTF8"), false);
    TL.writeString(buffer, phone_code_hash.getBytes("UTF8"), false);
    TL.writeString(buffer, phone_code.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SignIn: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(phone_number.getBytes("UTF8")) + TL.length(phone_code_hash.getBytes("UTF8")) + TL.length(phone_code.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(auth.signIn phone_number:" + "phone_number" + " phone_code_hash:" + "phone_code_hash" + " phone_code:" + "phone_code" + ")";
  }
}
