package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class Messages extends tl.geochats.TMessages {
  
  public Messages(ByteBuffer buffer) {
    messages = TL.readVector(buffer, true, new tl.TGeoChatMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Messages(tl.TGeoChatMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd1526db1);
    }
    TL.writeVector(buffer, messages, true, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 24 + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(Messages messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
