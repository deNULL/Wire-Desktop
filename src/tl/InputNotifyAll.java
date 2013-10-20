package tl;

import java.nio.ByteBuffer;

public class InputNotifyAll extends tl.TInputNotifyPeer {

  
  public InputNotifyAll(ByteBuffer buffer) {

  }
  
  public InputNotifyAll() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa429b886);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputNotifyAll)";
  }
}
