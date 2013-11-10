package tl;

import java.nio.ByteBuffer;

public class InitConnection extends tl.TLFunction {
  public int api_id;
  public String device_model;
  public String system_version;
  public String app_version;
  public String lang_code;
  public tl.TLObject query;
  
  public InitConnection(ByteBuffer buffer) throws Exception {
    api_id = buffer.getInt();
    device_model = new String(TL.readString(buffer), "UTF8");
    system_version = new String(TL.readString(buffer), "UTF8");
    app_version = new String(TL.readString(buffer), "UTF8");
    lang_code = new String(TL.readString(buffer), "UTF8");
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InitConnection(int api_id, String device_model, String system_version, String app_version, String lang_code, tl.TLObject query) {
    this.api_id = api_id;
    this.device_model = device_model;
    this.system_version = system_version;
    this.app_version = app_version;
    this.lang_code = lang_code;
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x69796de9);
    }
    buffer.putInt(api_id);
    TL.writeString(buffer, device_model.getBytes("UTF8"), false);
    TL.writeString(buffer, system_version.getBytes("UTF8"), false);
    TL.writeString(buffer, app_version.getBytes("UTF8"), false);
    TL.writeString(buffer, lang_code.getBytes("UTF8"), false);
    query.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InitConnection: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(device_model.getBytes("UTF8")) + TL.length(system_version.getBytes("UTF8")) + TL.length(app_version.getBytes("UTF8")) + TL.length(lang_code.getBytes("UTF8")) + query.length();
  }
  
  public String toString() {
    return "(initConnection api_id:" + api_id + " device_model:" + "device_model" + " system_version:" + "system_version" + " app_version:" + "app_version" + " lang_code:" + "lang_code" + " query:" + query + ")";
  }
}
