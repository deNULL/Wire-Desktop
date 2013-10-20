package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessage extends tl.geochats.TStatedMessage {
  
  public StatedMessage(ByteBuffer buffer) {
    message = (tl.TGeoChatMessage) TL.read(buffer);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    seq = buffer.getInt();
  }
  
  public StatedMessage(tl.TGeoChatMessage message, tl.TChat[] chats, tl.TUser[] users, int seq) {
    this.message = message;
    this.chats = chats;
    this.users = users;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x17b1578b);
    }
    message.writeTo(buffer, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 24 + message.length() + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(StatedMessage message:" + message + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " seq:" + seq + ")";
  }
}
