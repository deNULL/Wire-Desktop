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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc0e24635);
    }
    TL.writeString(buffer, random, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DhConfigNotModified: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(random);
  }
  
  public String toString() {
    return "(messages.dhConfigNotModified random:" + TL.toString(random) + ")";
  }
}
