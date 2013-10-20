package tl;

import java.nio.ByteBuffer;

public class UpdatesCombined extends tl.TUpdates {
  
  public UpdatesCombined(ByteBuffer buffer) {
    updates = TL.readVector(buffer, true, new tl.TUpdate[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    date = buffer.getInt();
    seq_start = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public UpdatesCombined(tl.TUpdate[] updates, tl.TUser[] users, tl.TChat[] chats, int date, int seq_start, int seq) {
    this.updates = updates;
    this.users = users;
    this.chats = chats;
    this.date = date;
    this.seq_start = seq_start;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x725b04c3);
    }
    TL.writeVector(buffer, updates, true, false);
    TL.writeVector(buffer, users, true, false);
    TL.writeVector(buffer, chats, true, false);
    buffer.putInt(date);
    buffer.putInt(seq_start);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 36 + TL.length(updates) + TL.length(users) + TL.length(chats);
  }
  
  public String toString() {
    return "(UpdatesCombined updates:" + TL.toString(updates) + " users:" + TL.toString(users) + " chats:" + TL.toString(chats) + " date:" + date + " seq_start:" + seq_start + " seq:" + seq + ")";
  }
}
