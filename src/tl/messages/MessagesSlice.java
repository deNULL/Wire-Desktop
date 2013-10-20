package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class MessagesSlice extends tl.messages.TMessages {
  
  public MessagesSlice(ByteBuffer buffer) {
    count = buffer.getInt();
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public MessagesSlice(int count, tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.count = count;
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb446ae3);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, messages, true, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 28 + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(MessagesSlice count:" + count + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
