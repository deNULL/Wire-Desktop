package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class MessagesSlice extends tl.geochats.TMessages {

  
  public MessagesSlice(ByteBuffer buffer) throws Exception {
    count = buffer.getInt();
    messages = TL.readVector(buffer, true, new tl.TGeoChatMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public MessagesSlice(int count, tl.TGeoChatMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.count = count;
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xbc5863e8);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, messages, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessagesSlice: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 28 + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(geochats.messagesSlice count:" + count + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
