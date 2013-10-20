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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x40e9002a);
    }
    chat.writeTo(buffer, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Chat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + chat.length() + TL.length(users);
  }
  
  public String toString() {
    return "(messages.chat chat:" + chat + " users:" + TL.toString(users) + ")";
  }
}
