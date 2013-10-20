package tl;

import java.nio.ByteBuffer;

public class ChatParticipants extends tl.TChatParticipants {

  
  public ChatParticipants(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    admin_id = buffer.getInt();
    participants = TL.readVector(buffer, true, new tl.TChatParticipant[0]);
    version = buffer.getInt();
  }
  
  public ChatParticipants(int chat_id, int admin_id, tl.TChatParticipant[] participants, int version) {
    this.chat_id = chat_id;
    this.admin_id = admin_id;
    this.participants = participants;
    this.version = version;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7841b415);
    }
    buffer.putInt(chat_id);
    buffer.putInt(admin_id);
    TL.writeVector(buffer, participants, true, true);
    buffer.putInt(version);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatParticipants: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(participants);
  }
  
  public String toString() {
    return "(chatParticipants chat_id:" + chat_id + " admin_id:" + admin_id + " participants:" + TL.toString(participants) + " version:" + version + ")";
  }
}
