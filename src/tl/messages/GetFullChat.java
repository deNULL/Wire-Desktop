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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3b831c66);
    }
    buffer.putInt(chat_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetFullChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(messages.getFullChat chat_id:" + chat_id + ")";
  }
}
