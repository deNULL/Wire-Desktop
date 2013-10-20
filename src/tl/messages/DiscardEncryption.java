package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DiscardEncryption extends tl.TLFunction {
  public int chat_id;
  
  public DiscardEncryption(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public DiscardEncryption(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xedd923c5);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(DiscardEncryption chat_id:" + chat_id + ")";
  }
}
