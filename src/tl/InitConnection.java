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
    device_model = new String(TL.readString(buffer));
    system_version = new String(TL.readString(buffer));
    app_version = new String(TL.readString(buffer));
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
    if (boxed) {
      buffer.putInt(0x3fc12e08);
    }
    buffer.putInt(api_id);
    TL.writeString(buffer, device_model.getBytes(), false);
    TL.writeString(buffer, system_version.getBytes(), false);
    TL.writeString(buffer, app_version.getBytes(), false);
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(device_model.getBytes()) + TL.length(system_version.getBytes()) + TL.length(app_version.getBytes()) + query.length();
  }
  
  public String toString() {
    return "(InitConnection api_id:" + api_id + " device_model:" + "device_model" + " system_version:" + "system_version" + " app_version:" + "app_version" + " query:" + query + ")";
  }
}
