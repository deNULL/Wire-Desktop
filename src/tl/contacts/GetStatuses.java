package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetStatuses extends tl.TLFunction {

  
  public GetStatuses(ByteBuffer buffer) {

  }
  
  public GetStatuses() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc4a353ee);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GetStatuses)";
  }
}
