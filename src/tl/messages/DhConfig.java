package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DhConfig extends tl.messages.TDhConfig {
  
  public DhConfig(ByteBuffer buffer) {
    g = buffer.getInt();
    p = TL.readString(buffer);
    version = buffer.getInt();
    random = TL.readString(buffer);
  }
  
  public DhConfig(int g, byte[] p, int version, byte[] random) {
    this.g = g;
    this.p = p;
    this.version = version;
    this.random = random;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2c221edd);
    }
    buffer.putInt(g);
    TL.writeString(buffer, p, false);
    buffer.putInt(version);
    TL.writeString(buffer, random, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(p) + TL.length(random);
  }
  
  public String toString() {
    return "(DhConfig g:" + g + " p:" + TL.toString(p) + " version:" + version + " random:" + TL.toString(random) + ")";
  }
}
