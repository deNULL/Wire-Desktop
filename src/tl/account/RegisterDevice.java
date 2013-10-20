package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class RegisterDevice extends tl.TLFunction {
  public int token_type;
  public String token;
  public String device_model;
  public String system_version;
  public String app_version;
  public boolean app_sandbox;
  public String lang_code;
  
  public RegisterDevice(ByteBuffer buffer) {
    token_type = buffer.getInt();
    token = new String(TL.readString(buffer));
    device_model = new String(TL.readString(buffer));
    system_version = new String(TL.readString(buffer));
    app_version = new String(TL.readString(buffer));
    app_sandbox = (buffer.getInt() == 0x997275b5);
    lang_code = new String(TL.readString(buffer));
  }
  
  public RegisterDevice(int token_type, String token, String device_model, String system_version, String app_version, boolean app_sandbox, String lang_code) {
    this.token_type = token_type;
    this.token = token;
    this.device_model = device_model;
    this.system_version = system_version;
    this.app_version = app_version;
    this.app_sandbox = app_sandbox;
    this.lang_code = lang_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x446c712c);
    }
    buffer.putInt(token_type);
    TL.writeString(buffer, token.getBytes(), false);
    TL.writeString(buffer, device_model.getBytes(), false);
    TL.writeString(buffer, system_version.getBytes(), false);
    TL.writeString(buffer, app_version.getBytes(), false);
    buffer.putInt(app_sandbox ? 0x997275b5 : 0xbc799737);
    TL.writeString(buffer, lang_code.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(token.getBytes()) + TL.length(device_model.getBytes()) + TL.length(system_version.getBytes()) + TL.length(app_version.getBytes()) + TL.length(lang_code.getBytes());
  }
  
  public String toString() {
    return "(RegisterDevice token_type:" + token_type + " token:" + "token" + " device_model:" + "device_model" + " system_version:" + "system_version" + " app_version:" + "app_version" + " app_sandbox:" + (app_sandbox ? "true" : "false") + " lang_code:" + "lang_code" + ")";
  }
}
