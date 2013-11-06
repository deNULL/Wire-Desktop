package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class InviteText extends tl.help.TInviteText {

  
  public InviteText(ByteBuffer buffer) {
    try {  message = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public InviteText(String message) {
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x18cb9f78);
    }
    try { TL.writeString(buffer, message.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InviteText: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(help.inviteText message:" + "message" + ")";
  }
}
