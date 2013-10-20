package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class MyLinkEmpty extends tl.contacts.TMyLink {

  
  public MyLinkEmpty(ByteBuffer buffer) {

  }
  
  public MyLinkEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd22a1c60);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(MyLinkEmpty)";
  }
}
