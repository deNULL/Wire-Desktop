package tl;

import java.nio.ByteBuffer;

public class UpdateNewAuthorization extends tl.TUpdate {

  
  public UpdateNewAuthorization(ByteBuffer buffer) throws Exception {
    auth_key_id = buffer.getLong();
    date = buffer.getInt();
    device = new String(TL.readString(buffer), "UTF8");
    location = new String(TL.readString(buffer), "UTF8");
  }
  
  public UpdateNewAuthorization(long auth_key_id, int date, String device, String location) {
    this.auth_key_id = auth_key_id;
    this.date = date;
    this.device = device;
    this.location = location;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8f06529a);
    }
    buffer.putLong(auth_key_id);
    buffer.putInt(date);
    TL.writeString(buffer, device.getBytes("UTF8"), false);
    TL.writeString(buffer, location.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNewAuthorization: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(device.getBytes("UTF8")) + TL.length(location.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(updateNewAuthorization auth_key_id:" + String.format("0x%016x", auth_key_id) + " date:" + date + " device:" + "device" + " location:" + "location" + ")";
  }
}
