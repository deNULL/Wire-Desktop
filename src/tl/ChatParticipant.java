package tl;

import java.nio.ByteBuffer;

public class ChatParticipant extends tl.TChatParticipant {
  
  public ChatParticipant(ByteBuffer buffer) {
    user_id = buffer.getInt();
    inviter_id = buffer.getInt();
    date = buffer.getInt();
  }
  
  public ChatParticipant(int user_id, int inviter_id, int date) {
    this.user_id = user_id;
    this.inviter_id = inviter_id;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc8d7493e);
    }
    buffer.putInt(user_id);
    buffer.putInt(inviter_id);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(ChatParticipant user_id:" + user_id + " inviter_id:" + inviter_id + " date:" + date + ")";
  }
}
