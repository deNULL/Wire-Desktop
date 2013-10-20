package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Blocked extends tl.contacts.TBlocked {
  
  public Blocked(ByteBuffer buffer) {
    blocked = TL.readVector(buffer, true, new tl.TContactBlocked[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Blocked(tl.TContactBlocked[] blocked, tl.TUser[] users) {
    this.blocked = blocked;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1c138d15);
    }
    TL.writeVector(buffer, blocked, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(blocked) + TL.length(users);
  }
  
  public String toString() {
    return "(Blocked blocked:" + TL.toString(blocked) + " users:" + TL.toString(users) + ")";
  }
}
