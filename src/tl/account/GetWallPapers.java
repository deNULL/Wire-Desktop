package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class GetWallPapers extends tl.TLFunction {

  
  public GetWallPapers(ByteBuffer buffer) {

  }
  
  public GetWallPapers() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc04cfac2);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(GetWallPapers)";
  }
}
