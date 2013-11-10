package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class GetConfig extends tl.TLFunction {

  
  public GetConfig(ByteBuffer buffer) throws Exception {

  }
  
  public GetConfig() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc4f9186b);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetConfig: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(help.getConfig)";
  }
}
