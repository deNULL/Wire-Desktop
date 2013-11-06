package tl;

import java.nio.ByteBuffer;

public class InitConnection extends tl.TLFunction {
  public int api_id;
  public String device_model;
  public String system_version;
  public String app_version;
  public tl.TLObject query;
  
  public InitConnection(ByteBuffer buffer) {
    api_id = buffer.getInt();
    try {  device_model = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  system_version = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  app_version = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InitConnection(int api_id, String device_model, String system_version, String app_version, tl.TLObject query) {
    this.api_id = api_id;
    this.device_model = device_model;
    this.system_version = system_version;
    this.app_version = app_version;
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3fc12e08);
    }
    buffer.putInt(api_id);
    try { TL.writeString(buffer, device_model.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, system_version.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, app_version.getBytes("UTF8"), false); } catch (Exception e) { };
    query.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InitConnection: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(device_model.getBytes()) + TL.length(system_version.getBytes()) + TL.length(app_version.getBytes()) + query.length();
  }
  
  public String toString() {
    return "(initConnection api_id:" + api_id + " device_model:" + "device_model" + " system_version:" + "system_version" + " app_version:" + "app_version" + " query:" + query + ")";
  }
}
