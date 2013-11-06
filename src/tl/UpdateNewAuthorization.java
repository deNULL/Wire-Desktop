package tl;

import java.nio.ByteBuffer;

public class UpdateNewAuthorization extends tl.TUpdate {

  
  public UpdateNewAuthorization(ByteBuffer buffer) {
    auth_key_id = buffer.getLong();
    date = buffer.getInt();
    try {  device = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  location = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public UpdateNewAuthorization(long auth_key_id, int date, String device, String location) {
    this.auth_key_id = auth_key_id;
    this.date = date;
    this.device = device;
    this.location = location;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8f06529a);
    }
    buffer.putLong(auth_key_id);
    buffer.putInt(date);
    try { TL.writeString(buffer, device.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, location.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNewAuthorization: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(device.getBytes()) + TL.length(location.getBytes());
  }
  
  public String toString() {
    return "(updateNewAuthorization auth_key_id:" + String.format("0x%016x", auth_key_id) + " date:" + date + " device:" + "device" + " location:" + "location" + ")";
  }
}
