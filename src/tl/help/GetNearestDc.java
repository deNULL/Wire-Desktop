package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class GetNearestDc extends tl.TLFunction {

  
  public GetNearestDc(ByteBuffer buffer) {

  }
  
  public GetNearestDc() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1fb33026);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetNearestDc: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(help.getNearestDc)";
  }
}
