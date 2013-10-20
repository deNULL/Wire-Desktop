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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7761198);
    }
    participants.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateChatParticipants: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + participants.length();
  }
  
  public String toString() {
    return "(updateChatParticipants participants:" + participants + ")";
  }
}
