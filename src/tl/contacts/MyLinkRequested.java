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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6c69efee);
    }
    buffer.putInt(contact ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MyLinkRequested: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(contacts.myLinkRequested contact:" + (contact ? "true" : "false") + ")";
  }
}
