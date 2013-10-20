package tl;

import java.nio.ByteBuffer;

public class InputNotifyUsers extends tl.TInputNotifyPeer {

  
  public InputNotifyUsers(ByteBuffer buffer) {

  }
  
  public InputNotifyUsers() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x193b4417);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputNotifyUsers: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputNotifyUsers)";
  }
}
