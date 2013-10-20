package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class Located extends tl.geochats.TLocated {
  
  public Located(ByteBuffer buffer) {
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x48feb267);
    }
    TL.writeVector(buffer, results, true, false);
    TL.writeVector(buffer, messages, true, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 32 + TL.length(results) + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(Located results:" + TL.toString(results) + " messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + ")";
  }
}
