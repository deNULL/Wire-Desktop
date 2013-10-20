package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class GetInviteText extends tl.TLFunction {
  public String lang_code;
  
  public GetInviteText(ByteBuffer buffer) {
    lang_code = new String(TL.readString(buffer));
  }
  
  public GetInviteText(String lang_code) {
    this.lang_code = lang_code;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa4a95186);
    }
    TL.writeString(buffer, lang_code.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetInviteText: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(lang_code.getBytes());
  }
  
  public String toString() {
    return "(help.getInviteText lang_code:" + "lang_code" + ")";
  }
}
