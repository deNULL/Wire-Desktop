package tl;

import java.nio.ByteBuffer;

public class InputNotifyUsers extends tl.TInputNotifyPeer {

  
  public InputNotifyUsers(ByteBuffer buffer) {

  }
  
  public InputNotifyUsers() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x193b4417);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputNotifyUsers)";
  }
}
