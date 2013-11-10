package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class InviteText extends tl.help.TInviteText {

  
  public InviteText(ByteBuffer buffer) throws Exception {
    message = new String(TL.readString(buffer), "UTF8");
  }
  
  public InviteText(String message) {
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x18cb9f78);
    }
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InviteText: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(message.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(help.inviteText message:" + "message" + ")";
  }
}
