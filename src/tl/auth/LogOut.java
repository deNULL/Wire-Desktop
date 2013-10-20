package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class LogOut extends tl.TLFunction {

  
  public LogOut(ByteBuffer buffer) {

  }
  
  public LogOut() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5717da40);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(LogOut)";
  }
}
