package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DiscardEncryption extends tl.TLFunction {
  public int chat_id;
  
  public DiscardEncryption(ByteBuffer buffer) throws Exception {
    chat_id = buffer.getInt();
  }
  
  public DiscardEncryption(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xedd923c5);
    }
    buffer.putInt(chat_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DiscardEncryption: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(messages.discardEncryption chat_id:" + chat_id + ")";
  }
}
