package tl;

import java.nio.ByteBuffer;

public class PeerChat extends tl.TPeer {

  
  public PeerChat(ByteBuffer buffer) throws Exception {
    chat_id = buffer.getInt();
  }
  
  public PeerChat(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xbad0e5bb);
    }
    buffer.putInt(chat_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PeerChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(peerChat chat_id:" + chat_id + ")";
  }
}
