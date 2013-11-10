package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SendInvites extends tl.TLFunction {
  public String[] phone_numbers;
  public String message;
  
  public SendInvites(ByteBuffer buffer) throws Exception {
    phone_numbers = TL.readVectorString(buffer, true);
    message = new String(TL.readString(buffer), "UTF8");
  }
  
  public SendInvites(String[] phone_numbers, String message) {
    this.phone_numbers = phone_numbers;
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x771c1d97);
    }
    TL.writeVector(buffer, phone_numbers, true, false);
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendInvites: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(phone_numbers) + TL.length(message.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(auth.sendInvites phone_numbers:" + TL.toString(phone_numbers) + " message:" + "message" + ")";
  }
}
