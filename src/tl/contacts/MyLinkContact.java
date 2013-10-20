package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class MyLinkContact extends tl.contacts.TMyLink {

  
  public MyLinkContact(ByteBuffer buffer) {

  }
  
  public MyLinkContact() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc240ebd9);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MyLinkContact)";
  }
}
