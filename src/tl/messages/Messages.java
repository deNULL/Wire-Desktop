package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Messages extends tl.messages.TMessages {

  
  public Messages(ByteBuffer buffer) throws Exception {
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Messages(tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8c718e87);
    }
    TL.writeVector(buffer, messages, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Messages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 24 + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.messages messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
