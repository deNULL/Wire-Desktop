package tl;

import java.nio.ByteBuffer;

public class ChatParticipantsForbidden extends tl.TChatParticipants {
  
  public ChatParticipantsForbidden(ByteBuffer buffer) {
    chat_id = buffer.getInt();
  }
  
  public ChatParticipantsForbidden(int chat_id) {
    this.chat_id = chat_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xfd2bb8a);
    }
    buffer.putInt(chat_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ChatParticipantsForbidden chat_id:" + chat_id + ")";
  }
}
