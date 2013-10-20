package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Chat extends tl.messages.TChat {
  
  public Chat(ByteBuffer buffer) {
    chat = (tl.TChat) TL.read(buffer);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Chat(tl.TChat chat, tl.TUser[] users) {
    this.chat = chat;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x40e9002a);
    }
    chat.writeTo(buffer, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + chat.length() + TL.length(users);
  }
  
  public String toString() {
    return "(Chat chat:" + chat + " users:" + TL.toString(users) + ")";
  }
}
