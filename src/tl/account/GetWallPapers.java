package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class GetWallPapers extends tl.TLFunction {

  
  public GetWallPapers(ByteBuffer buffer) throws Exception {

  }
  
  public GetWallPapers() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc04cfac2);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetWallPapers: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(account.getWallPapers)";
  }
}
