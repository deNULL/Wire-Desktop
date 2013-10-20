package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SendCall extends tl.TLFunction {
  public String phone_number;
  public String phone_code_hash;
  
  public SendCall(ByteBuffer buffer) {
    phone_number = new String(TL.readString(buffer));
    phone_code_hash = new String(TL.readString(buffer));
  }
  
  public SendCall(String phone_number, String phone_code_hash) {
    this.phone_number = phone_number;
    this.phone_code_hash = phone_code_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3c51564);
    }
    TL.writeString(buffer, phone_number.getBytes(), false);
    TL.writeString(buffer, phone_code_hash.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes()) + TL.length(phone_code_hash.getBytes());
  }
  
  public String toString() {
    return "(SendCall phone_number:" + "phone_number" + " phone_code_hash:" + "phone_code_hash" + ")";
  }
}
