package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkRequested extends tl.contacts.TForeignLink {
  
  public ForeignLinkRequested(ByteBuffer buffer) {
    has_phone = (buffer.getInt() == 0x997275b5);
  }
  
  public ForeignLinkRequested(boolean has_phone) {
    this.has_phone = has_phone;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa7801f47);
    }
    buffer.putInt(has_phone ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ForeignLinkRequested has_phone:" + (has_phone ? "true" : "false") + ")";
  }
}
