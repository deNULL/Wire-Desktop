package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Dialogs extends tl.messages.TDialogs {

  
  public Dialogs(ByteBuffer buffer) throws Exception {
    dialogs = TL.readVector(buffer, true, new tl.TDialog[0]);
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Dialogs(tl.TDialog[] dialogs, tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.dialogs = dialogs;
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x15ba6c40);
    }
    TL.writeVector(buffer, dialogs, true, true);
    TL.writeVector(buffer, messages, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Dialogs: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 32 + TL.length(dialogs) + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.dialogs dialogs:" + TL.toString(dialogs) + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
