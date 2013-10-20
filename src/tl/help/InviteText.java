package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class InviteText extends tl.help.TInviteText {
  
  public InviteText(ByteBuffer buffer) {
    message = new String(TL.readString(buffer));
  }
  
  public InviteText(String message) {
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x18cb9f78);
    }
    TL.writeString(buffer, message.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(InviteText message:" + "message" + ")";
  }
}
