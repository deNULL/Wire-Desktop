package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class Located extends tl.geochats.TLocated {

  
  public Located(ByteBuffer buffer) throws Exception {
    results = TL.readVector(buffer, true, new tl.TChatLocated[0]);
    messages = TL.readVector(buffer, true, new tl.TGeoChatMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Located(tl.TChatLocated[] results, tl.TGeoChatMessage[] messages, tl.TChat[] chats, tl.TUser[] users) {
    this.results = results;
    this.messages = messages;
    this.chats = chats;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x48feb267);
    }
    TL.writeVector(buffer, results, true, true);
    TL.writeVector(buffer, messages, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Located: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 32 + TL.length(results) + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(geochats.located results:" + TL.toString(results) + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
