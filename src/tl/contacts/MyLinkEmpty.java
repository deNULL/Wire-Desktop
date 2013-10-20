package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class MyLinkEmpty extends tl.contacts.TMyLink {

  
  public MyLinkEmpty(ByteBuffer buffer) {

  }
  
  public MyLinkEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd22a1c60);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MyLinkEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(contacts.myLinkEmpty)";
  }
}
