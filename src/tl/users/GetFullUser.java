package tl.users;

import tl.TL;
import java.nio.ByteBuffer;

public class GetFullUser extends tl.TLFunction {
  public tl.TInputUser id;
  
  public GetFullUser(ByteBuffer buffer) {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public GetFullUser(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xca30a5b1);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetFullUser: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(users.getFullUser id:" + id + ")";
  }
}
