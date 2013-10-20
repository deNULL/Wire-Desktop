package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkMutual extends tl.contacts.TForeignLink {

  
  public ForeignLinkMutual(ByteBuffer buffer) {

  }
  
  public ForeignLinkMutual() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1bea8ce1);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ForeignLinkMutual: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(contacts.foreignLinkMutual)";
  }
}
