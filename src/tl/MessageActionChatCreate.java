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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa6638b9a);
    }
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeVector(buffer, users, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatCreate: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(title.getBytes()) + users.length * 4;
  }
  
  public String toString() {
    return "(messageActionChatCreate title:" + "title" + " users:" + TL.toString(users) + ")";
  }
}
