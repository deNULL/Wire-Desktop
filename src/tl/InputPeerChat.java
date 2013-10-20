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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x179be863);
    }
    buffer.putInt(chat_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(inputPeerChat chat_id:" + chat_id + ")";
  }
}
