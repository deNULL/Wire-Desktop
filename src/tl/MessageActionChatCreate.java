package tl;

import java.nio.ByteBuffer;

public class MessageActionChatCreate extends tl.TMessageAction {
  
  public MessageActionChatCreate(ByteBuffer buffer) {
    title = new String(TL.readString(buffer));
    users = TL.readVectorInt(buffer, true);
  }
  
  public MessageActionChatCreate(String title, int[] users) {
    this.title = title;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa6638b9a);
    }
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(title.getBytes()) + users.length * 4;
  }
  
  public String toString() {
    return "(MessageActionChatCreate title:" + "title" + " users:" + TL.toString(users) + ")";
  }
}
