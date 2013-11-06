package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SendCode extends tl.TLFunction {
  public String phone_number;
  public int sms_type;
  public int api_id;
  public String api_hash;
  public String lang_code;
  
  public SendCode(ByteBuffer buffer) {
    try {  phone_number = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    sms_type = buffer.getInt();
    api_id = buffer.getInt();
    try {  api_hash = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  lang_code = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public SendCode(String phone_number, int sms_type, int api_id, String api_hash, String lang_code) {
    this.phone_number = phone_number;
    this.sms_type = sms_type;
    this.api_id = api_id;
    this.api_hash = api_hash;
    this.lang_code = lang_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x768d5f4d);
    }
    try { TL.writeString(buffer, phone_number.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(sms_type);
    buffer.putInt(api_id);
    try { TL.writeString(buffer, api_hash.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, lang_code.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendCode: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(phone_number.getBytes()) + TL.length(api_hash.getBytes()) + TL.length(lang_code.getBytes());
  }
  
  public String toString() {
    return "(auth.sendCode phone_number:" + "phone_number" + " sms_type:" + sms_type + " api_id:" + api_id + " api_hash:" + "api_hash" + " lang_code:" + "lang_code" + ")";
  }
}
