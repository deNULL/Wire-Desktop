package tl;

import java.nio.ByteBuffer;

public class ChatParticipantsForbidden extends tl.TChatParticipants {

  
  public ChatParticipantsForbidden(ByteBuffer buffer) throws Exception {
    chat_id = buffer.getInt();
  }
  
  public ChatParticipantsForbidden(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xfd2bb8a);
    }
    buffer.putInt(chat_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatParticipantsForbidden: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(chatParticipantsForbidden chat_id:" + chat_id + ")";
  }
}
