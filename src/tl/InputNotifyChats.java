package tl;

import java.nio.ByteBuffer;

public class InputNotifyChats extends tl.TInputNotifyPeer {

  
  public InputNotifyChats(ByteBuffer buffer) {

  }
  
  public InputNotifyChats() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4a95e84e);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputNotifyChats: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(inputNotifyChats)";
  }
}
