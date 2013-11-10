package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessage extends tl.geochats.TStatedMessage {

  
  public StatedMessage(ByteBuffer buffer) throws Exception {
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x17b1578b);
    }
    message.writeTo(buffer, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at StatedMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 24 + message.length() + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(geochats.statedMessage message:" + message + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " seq:" + seq + ")";
  }
}
