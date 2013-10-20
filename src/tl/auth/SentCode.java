package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class SentCode extends tl.auth.TSentCode {

  
  public SentCode(ByteBuffer buffer) {
    phone_registered = (buffer.getInt() == 0x997275b5);
    phone_code_hash = new String(TL.readString(buffer));
  }
  
  public SentCode(boolean phone_registered, String phone_code_hash) {
    this.phone_registered = phone_registered;
    this.phone_code_hash = phone_code_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2215bcbd);
    }
    buffer.putInt(phone_registered ? 0x997275b5 : 0xbc799737);
    TL.writeString(buffer, phone_code_hash.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SentCode: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(phone_code_hash.getBytes());
  }
  
  public String toString() {
    return "(auth.sentCode phone_registered:" + (phone_registered ? "true" : "false") + " phone_code_hash:" + "phone_code_hash" + ")";
  }
}
