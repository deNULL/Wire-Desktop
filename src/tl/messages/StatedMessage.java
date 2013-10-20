package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class StatedMessage extends tl.messages.TStatedMessage {

  
  public StatedMessage(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    pts = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public StatedMessage(tl.TMessage message, tl.TChat[] chats, tl.TUser[] users, int pts, int seq) {
    this.message = message;
    this.chats = chats;
    this.users = users;
    this.pts = pts;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd07ae726);
    }
    message.writeTo(buffer, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    buffer.putInt(pts);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at StatedMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 28 + message.length() + TL.length(chats) + TL.length(users);
  }
  
  public String toString() {
    return "(messages.statedMessage message:" + message + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " pts:" + pts + " seq:" + seq + ")";
  }
}
