package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetFullChat extends tl.TLFunction {
  public int chat_id;
  
  public GetFullChat(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public GetFullChat(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3b831c66);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(GetFullChat chat_id:" + chat_id + ")";
  }
}
