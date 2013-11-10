package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Chats extends tl.messages.TChats {

  
  public Chats(ByteBuffer buffer) throws Exception {
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Chats(tl.TChat[] chats, tl.TUser[] users) {
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8150cbd8);
    }
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Chats: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.chats chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
