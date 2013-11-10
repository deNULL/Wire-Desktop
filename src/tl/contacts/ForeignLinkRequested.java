package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkRequested extends tl.contacts.TForeignLink {

  
  public ForeignLinkRequested(ByteBuffer buffer) throws Exception {
    has_phone = (buffer.getInt() == 0x997275b5);
  }
  
  public ForeignLinkRequested(boolean has_phone) {
    this.has_phone = has_phone;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa7801f47);
    }
    buffer.putInt(has_phone ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ForeignLinkRequested: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(contacts.foreignLinkRequested has_phone:" + (has_phone ? "true" : "false") + ")";
  }
}
