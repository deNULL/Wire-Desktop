package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDhConfig extends tl.TLFunction {
  public int version;
  public int random_length;
  
  public GetDhConfig(ByteBuffer buffer) throws Exception {
    version = buffer.getInt();
    random_length = buffer.getInt();
  }
  
  public GetDhConfig(int version, int random_length) {
    this.version = version;
    this.random_length = random_length;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x26cf8950);
    }
    buffer.putInt(version);
    buffer.putInt(random_length);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetDhConfig: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(messages.getDhConfig version:" + version + " random_length:" + random_length + ")";
  }
}
