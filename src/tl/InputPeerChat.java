package tl;

import java.nio.ByteBuffer;

public class InputPeerChat extends tl.TInputPeer {
  
  public InputPeerChat(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public InputPeerChat(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x179be863);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(InputPeerChat chat_id:" + chat_id + ")";
  }
}
