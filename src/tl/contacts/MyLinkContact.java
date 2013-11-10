package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class MyLinkContact extends tl.contacts.TMyLink {

  
  public MyLinkContact(ByteBuffer buffer) throws Exception {

  }
  
  public MyLinkContact() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc240ebd9);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MyLinkContact: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(contacts.myLinkContact)";
  }
}
