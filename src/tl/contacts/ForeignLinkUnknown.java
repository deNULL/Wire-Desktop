package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkUnknown extends tl.contacts.TForeignLink {

  
  public ForeignLinkUnknown(ByteBuffer buffer) {

  }
  
  public ForeignLinkUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x133421f8);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ForeignLinkUnknown: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(contacts.foreignLinkUnknown)";
  }
}
