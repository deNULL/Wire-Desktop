package tl;

import java.nio.ByteBuffer;

public class UpdateChatParticipants extends tl.TUpdate {
  
  public UpdateChatParticipants(ByteBuffer buffer) {
    participants = (tl.TChatParticipants) TL.read(buffer);
  }
  
  public UpdateChatParticipants(tl.TChatParticipants participants) {
    this.participants = participants;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7761198);
    }
    participants.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + participants.length();
  }
  
  public String toString() {
    return "(UpdateChatParticipants participants:" + participants + ")";
  }
}
