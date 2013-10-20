package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Found extends tl.contacts.TFound {
  
  public Found(ByteBuffer buffer) {
    results = TL.readVector(buffer, true, new tl.TContactFound[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
  }
  
  public Found(tl.TContactFound[] results, tl.TUser[] users) {
    this.results = results;
    this.users = users;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x566000e);
    }
    TL.writeVector(buffer, results, true, false);
    TL.writeVector(buffer, users, true, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(results) + TL.length(users);
  }
  
  public String toString() {
    return "(Found results:" + TL.toString(results) + " users:" + TL.toString(users) + ")";
  }
}
