package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Message extends tl.messages.TMessage {

  
  public Message(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Message(tl.TMessage message, tl.TChat[] chats, tl.TUser[] users) {
    this.message = message;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xff90c417);
    }
    message.writeTo(buffer, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Message: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + message.length() + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.message message:" + message + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
