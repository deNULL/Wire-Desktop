package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class CheckPhone extends tl.TLFunction {
  public String phone_number;
  
  public CheckPhone(ByteBuffer buffer) throws Exception {
    phone_number = new String(TL.readString(buffer), "UTF8");
  }
  
  public CheckPhone(String phone_number) {
    this.phone_number = phone_number;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6fe51dfb);
    }
    TL.writeString(buffer, phone_number.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at CheckPhone: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(phone_number.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(auth.checkPhone phone_number:" + "phone_number" + ")";
  }
}
