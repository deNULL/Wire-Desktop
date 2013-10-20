package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class Chats extends tl.messages.TChats {
  
  public Chats(ByteBuffer buffer) {
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Chats(tl.TChat[] chats, tl.TUser[] users) {
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x8150cbd8);
    }
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(Chats chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
