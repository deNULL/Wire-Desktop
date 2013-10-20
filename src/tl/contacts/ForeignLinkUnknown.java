package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class ForeignLinkUnknown extends tl.contacts.TForeignLink {

  
  public ForeignLinkUnknown(ByteBuffer buffer) {

  }
  
  public ForeignLinkUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x133421f8);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ForeignLinkUnknown)";
  }
}
