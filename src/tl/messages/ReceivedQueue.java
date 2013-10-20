package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReceivedQueue extends tl.TLFunction {
  public int max_qts;
  
  public ReceivedQueue(ByteBuffer buffer) {
    max_qts = buffer.getInt();
  }
  
  public ReceivedQueue(int max_qts) {
    this.max_qts = max_qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x55a5bb66);
    }
    buffer.putInt(max_qts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReceivedQueue: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(messages.receivedQueue max_qts:" + max_qts + ")";
  }
}
