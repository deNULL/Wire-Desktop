package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SendInvites extends tl.TLFunction {
  public String[] phone_numbers;
  public String message;
  
  public SendInvites(ByteBuffer buffer) {
    phone_numbers = TL.readVectorString(buffer, true);
    message = new String(TL.readString(buffer));
  }
  
  public SendInvites(String[] phone_numbers, String message) {
    this.phone_numbers = phone_numbers;
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x771c1d97);
    }
    TL.writeVector(buffer, phone_numbers, true, false);
    TL.writeString(buffer, message.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(phone_numbers) + TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(SendInvites phone_numbers:" + TL.toString(phone_numbers) + " message:" + "message" + ")";
  }
}
