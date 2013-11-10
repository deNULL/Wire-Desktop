package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReceivedMessages extends tl.TLFunction {
  public int max_id;
  
  public ReceivedMessages(ByteBuffer buffer) throws Exception {
    max_id = buffer.getInt();
  }
  
  public ReceivedMessages(int max_id) {
    this.max_id = max_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x28abcb68);
    }
    buffer.putInt(max_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReceivedMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(messages.receivedMessages max_id:" + max_id + ")";
  }
}
