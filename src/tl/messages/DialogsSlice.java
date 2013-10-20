package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DialogsSlice extends tl.messages.TDialogs {
  
  public DialogsSlice(ByteBuffer buffer) {
    count = buffer.getInt();
    dialogs = TL.readVector(buffer, true, new tl.TDialog[0]);
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public DialogsSlice(int count, tl.TDialog[] dialogs, tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.count = count;
    this.dialogs = dialogs;
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x71e094f3);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, dialogs, true, false);
    TL.writeVector(buffer, messages, true, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 36 + TL.length(dialogs) + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(DialogsSlice count:" + count + " dialogs:" + TL.toString(dialogs) + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
