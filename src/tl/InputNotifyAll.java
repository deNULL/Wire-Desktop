package tl;

import java.nio.ByteBuffer;

public class InputNotifyAll extends tl.TInputNotifyPeer {

  
  public InputNotifyAll(ByteBuffer buffer) throws Exception {

  }
  
  public InputNotifyAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa429b886);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputNotifyAll: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputNotifyAll)";
  }
}
