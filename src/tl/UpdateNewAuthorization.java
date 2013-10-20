package tl;

import java.nio.ByteBuffer;

public class UpdateNewAuthorization extends tl.TUpdate {
  
  public UpdateNewAuthorization(ByteBuffer buffer) {
    auth_key_id = buffer.getLong();
    date = buffer.getInt();
    device = new String(TL.readString(buffer));
    location = new String(TL.readString(buffer));
  }
  
  public UpdateNewAuthorization(long auth_key_id, int date, String device, String location) {
    this.auth_key_id = auth_key_id;
    this.date = date;
    this.device = device;
    this.location = location;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8f06529a);
    }
    buffer.putLong(auth_key_id);
    buffer.putInt(date);
    TL.writeString(buffer, device.getBytes(), false);
    TL.writeString(buffer, location.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(device.getBytes()) + TL.length(location.getBytes());
  }
  
  public String toString() {
    return "(UpdateNewAuthorization auth_key_id:" + String.format("0x%016x", auth_key_id) + " date:" + date + " device:" + "device" + " location:" + "location" + ")";
  }
}
