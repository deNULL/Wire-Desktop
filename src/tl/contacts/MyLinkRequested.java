package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class MyLinkRequested extends tl.contacts.TMyLink {
  
  public MyLinkRequested(ByteBuffer buffer) {
    contact = (buffer.getInt() == 0x997275b5);
  }
  
  public MyLinkRequested(boolean contact) {
    this.contact = contact;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6c69efee);
    }
    buffer.putInt(contact ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(MyLinkRequested contact:" + (contact ? "true" : "false") + ")";
  }
}
