package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SendCall extends tl.TLFunction {
  public String phone_number;
  public String phone_code_hash;
  
  public SendCall(ByteBuffer buffer) {
    try {  phone_number = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  phone_code_hash = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public SendCall(String phone_number, String phone_code_hash) {
    this.phone_number = phone_number;
    this.phone_code_hash = phone_code_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3c51564);
    }
    try { TL.writeString(buffer, phone_number.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, phone_code_hash.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendCall: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(phone_number.getBytes()) + TL.length(phone_code_hash.getBytes());
  }
  
  public String toString() {
    return "(auth.sendCall phone_number:" + "phone_number" + " phone_code_hash:" + "phone_code_hash" + ")";
  }
}
