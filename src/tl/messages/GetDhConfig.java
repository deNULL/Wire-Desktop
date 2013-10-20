package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDhConfig extends tl.TLFunction {
  public int version;
  public int random_length;
  
  public GetDhConfig(ByteBuffer buffer) {
    version = buffer.getInt();
    random_length = buffer.getInt();
  }
  
  public GetDhConfig(int version, int random_length) {
    this.version = version;
    this.random_length = random_length;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x26cf8950);
    }
    buffer.putInt(version);
    buffer.putInt(random_length);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(GetDhConfig version:" + version + " random_length:" + random_length + ")";
  }
}
