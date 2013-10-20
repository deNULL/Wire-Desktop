package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class ResetAuthorizations extends tl.TLFunction {

  
  public ResetAuthorizations(ByteBuffer buffer) {

  }
  
  public ResetAuthorizations() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9fab0d1a);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ResetAuthorizations: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(auth.resetAuthorizations)";
  }
}
