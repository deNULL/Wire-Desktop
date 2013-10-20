package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkMutual extends tl.contacts.TForeignLink {

  
  public ForeignLinkMutual(ByteBuffer buffer) {

  }
  
  public ForeignLinkMutual() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1bea8ce1);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ForeignLinkMutual)";
  }
}
