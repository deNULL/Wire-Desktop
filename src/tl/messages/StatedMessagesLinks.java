package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessagesLinks extends tl.messages.TStatedMessages {
  
  public StatedMessagesLinks(ByteBuffer buffer) {
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    links = TL.readVector(buffer, true, new tl.contacts.TLink[0]);
    pts = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public StatedMessagesLinks(tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users, tl.contacts.TLink[] links, int pts, int seq) {
    this.messages = messages;
    this.chats = chats;
    this.users = users;
    this.links = links;
    this.pts = pts;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3e74f5c6);
    }
    TL.writeVector(buffer, messages, true, false);
    TL.writeVector(buffer, chats, true, false);
    TL.writeVector(buffer, users, true, false);
    TL.writeVector(buffer, links, true, false);
    buffer.putInt(pts);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 40 + TL.length(messages) + TL.length(chats) + TL.length(users) + TL.length(links);
  }
  
  public String toString() {
    return "(StatedMessagesLinks messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " links:" + TL.toString(links) + " pts:" + pts + " seq:" + seq + ")";
  }
}
