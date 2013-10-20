package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class GetNearestDc extends tl.TLFunction {

  
  public GetNearestDc(ByteBuffer buffer) {

  }
  
  public GetNearestDc() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1fb33026);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GetNearestDc)";
  }
}
