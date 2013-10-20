package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessageLink extends tl.messages.TStatedMessage {
  
  public StatedMessageLink(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    links = TL.readVector(buffer, true, new tl.contacts.TLink[0]);
    pts = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public StatedMessageLink(tl.TMessage message, tl.TChat[] chats, tl.TUser[] users, tl.contacts.TLink[] links, int pts, int seq) {
    this.message = message;
    this.chats = chats;
    this.users = users;
    this.links = links;
    this.pts = pts;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa9af2881);
    }
    message.writeTo(buffer, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
    TL.writeVector(buffer, links, true, false);
    buffer.putInt(pts);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 36 + message.length() + TL.length(chats) + TL.length(users) + TL.length(links);
  }
  
  public String toString() {
    return "(StatedMessageLink message:" + message + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " links:" + TL.toString(links) + " pts:" + pts + " seq:" + seq + ")";
  }
}
