package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class GetConfig extends tl.TLFunction {

  
  public GetConfig(ByteBuffer buffer) {

  }
  
  public GetConfig() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc4f9186b);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GetConfig)";
  }
}
