package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessages extends tl.messages.TStatedMessages {

  
  public StatedMessages(ByteBuffer buffer) {
    messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    pts = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public StatedMessages(tl.TMessage[] messages, tl.TChat[] chats, tl.TUser[] users, int pts, int seq) {
    this.messages = messages;
    this.chats = chats;
    this.users = users;
    this.pts = pts;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x969478bb);
    }
    TL.writeVector(buffer, messages, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    buffer.putInt(pts);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at StatedMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 32 + TL.length(messages) + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.statedMessages messages:" + TL.toString(messages) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " pts:" + pts + " seq:" + seq + ")";
  }
}
