package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DhConfigNotModified extends tl.messages.TDhConfig {
  
  public DhConfigNotModified(ByteBuffer buffer) {
    random = TL.readString(buffer);
  }
  
  public DhConfigNotModified(byte[] random) {
    this.random = random;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc0e24635);
    }
    TL.writeString(buffer, random, false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(random);
  }
  
  public String toString() {
    return "(DhConfigNotModified random:" + TL.toString(random) + ")";
  }
}
