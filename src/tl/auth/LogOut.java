package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class LogOut extends tl.TLFunction {

  
  public LogOut(ByteBuffer buffer) throws Exception {

  }
  
  public LogOut() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5717da40);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at LogOut: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(auth.logOut)";
  }
}
