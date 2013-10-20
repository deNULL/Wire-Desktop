package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class ResetAuthorizations extends tl.TLFunction {

  
  public ResetAuthorizations(ByteBuffer buffer) {

  }
  
  public ResetAuthorizations() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9fab0d1a);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ResetAuthorizations)";
  }
}
