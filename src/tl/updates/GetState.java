package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class GetState extends tl.TLFunction {

  
  public GetState(ByteBuffer buffer) {

  }
  
  public GetState() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xedd4882a);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetState: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(updates.getState)";
  }
}
