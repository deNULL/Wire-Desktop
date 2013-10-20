package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class CheckPhone extends tl.TLFunction {
  public String phone_number;
  
  public CheckPhone(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
  }
  
  public CheckPhone(String phone_number) {
    this.phone_number = phone_number;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6fe51dfb);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes());
  }
  
  public String toString() {
    return "(CheckPhone phone_number:" + "phone_number" + ")";
  }
}
