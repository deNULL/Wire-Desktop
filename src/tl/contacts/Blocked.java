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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1c138d15);
    }
    TL.writeVector(buffer, blocked, true, true);
    TL.writeVector(buffer, users, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Blocked: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(blocked) + TL.length(users);
  }
  
  public String toString() {
    return "(contacts.blocked blocked:" + TL.toString(blocked) + " users:" + TL.toString(users) + ")";
  }
}
