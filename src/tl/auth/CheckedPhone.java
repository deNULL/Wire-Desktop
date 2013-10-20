package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class CheckedPhone extends tl.auth.TCheckedPhone {
  
  public CheckedPhone(ByteBuffer buffer) {
    phone_registered = (buffer.getInt() == 0x997275b5);
    phone_invited = (buffer.getInt() == 0x997275b5);
  }
  
  public CheckedPhone(boolean phone_registered, boolean phone_invited) {
    this.phone_registered = phone_registered;
    this.phone_invited = phone_invited;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe300cc3b);
    }
    buffer.putInt(phone_registered ? 0x997275b5 : 0xbc799737);
    buffer.putInt(phone_invited ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(CheckedPhone phone_registered:" + (phone_registered ? "true" : "false") + " phone_invited:" + (phone_invited ? "true" : "false") + ")";
  }
}
