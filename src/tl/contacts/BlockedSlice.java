package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class BlockedSlice extends tl.contacts.TBlocked {

  
  public BlockedSlice(ByteBuffer buffer) throws Exception {
    count = buffer.getInt();
    blocked = TL.readVector(buffer, true, new tl.TContactBlocked[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public BlockedSlice(int count, tl.TContactBlocked[] blocked, tl.TUser[] users) {
    this.count = count;
    this.blocked = blocked;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x900802a1);
    }
    buffer.putInt(count);
    TL.writeVector(buffer, blocked, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at BlockedSlice: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + TL.length(blocked) + TL.length(users);
  }
  
  public String toString() {
    return "(contacts.blockedSlice count:" + count + " blocked:" + TL.toString(blocked) + " users:" + TL.toString(users) + ")";
  }
}
