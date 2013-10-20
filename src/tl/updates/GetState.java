package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class GetState extends tl.TLFunction {

  
  public GetState(ByteBuffer buffer) {

  }
  
  public GetState() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xedd4882a);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GetState)";
  }
}
