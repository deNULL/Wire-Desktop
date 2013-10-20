package tl;

import java.nio.ByteBuffer;

public class PeerChat extends tl.TPeer {
  
  public PeerChat(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public PeerChat(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xbad0e5bb);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(PeerChat chat_id:" + chat_id + ")";
  }
}
