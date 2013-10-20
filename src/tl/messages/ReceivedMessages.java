package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReceivedMessages extends tl.TLFunction {
  public int max_id;
  
  public ReceivedMessages(ByteBuffer buffer) {
    max_id = buffer.getInt();
  }
  
  public ReceivedMessages(int max_id) {
    this.max_id = max_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x28abcb68);
    }
    buffer.putInt(max_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ReceivedMessages max_id:" + max_id + ")";
  }
}
