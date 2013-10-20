package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class BlockedSlice extends tl.contacts.TBlocked {
  public int count;
  public tl.TContactBlocked[] blocked;
  public tl.TUser[] users;
  
  public BlockedSlice(ByteBuffer buffer) {
    count = buffer.getInt();
    blocked = TL.readVector(buffer, true, new tl.TContactBlocked[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public BlockedSlice(int count, tl.TContactBlocked[] blocked, tl.TUser[] users) {
    this.count = count;
    this.blocked = blocked;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x900802a1);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, blocked, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(blocked) + TL.length(users);
  }
  
  public String toString() {
    return "(BlockedSlice count:" + count + " blocked:" + TL.toString(blocked) + " users:" + TL.toString(users) + ")";
  }
}
