package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetStatuses extends tl.TLFunction {

  
  public GetStatuses(ByteBuffer buffer) throws Exception {

  }
  
  public GetStatuses() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc4a353ee);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetStatuses: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(contacts.getStatuses)";
  }
}
