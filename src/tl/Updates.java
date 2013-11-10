package tl;

import java.nio.ByteBuffer;

public class Updates extends tl.TUpdates {

  
  public Updates(ByteBuffer buffer) throws Exception {
    updates = TL.readVector(buffer, true, new tl.TUpdate[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    date = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public Updates(tl.TUpdate[] updates, tl.TUser[] users, tl.TChat[] chats, int date, int seq) {
    this.updates = updates;
    this.users = users;
    this.chats = chats;
    this.date = date;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x74ae4240);
    }
    TL.writeVector(buffer, updates, true, true);
    TL.writeVector(buffer, users, true, true);
    TL.writeVector(buffer, chats, true, true);
    buffer.putInt(date);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Updates: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 32 + TL.length(updates) + TL.length(users) + TL.length(chats);
  }
  
  public String toString() {
    return "(updates updates:" + TL.toString(updates) + " users:" + TL.toString(users) + " chats:" + TL.toString(chats) + " date:" + date + " seq:" + seq + ")";
  }
}
