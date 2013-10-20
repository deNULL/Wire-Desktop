package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ChatFull extends tl.messages.TChatFull {
  
  public ChatFull(ByteBuffer buffer) {
    full_chat = (tl.TChatFull) TL.read(buffer);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public ChatFull(tl.TChatFull full_chat, tl.TChat[] chats, tl.TUser[] users) {
    this.full_chat = full_chat;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe5d7d19c);
    }
    full_chat.writeTo(buffer, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 20 + full_chat.length() + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(ChatFull full_chat:" + full_chat + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
