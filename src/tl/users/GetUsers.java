package tl.users;

import tl.TL;
import java.nio.ByteBuffer;

public class GetUsers extends tl.TLFunction {
  public tl.TInputUser[] id;
  
  public GetUsers(ByteBuffer buffer) {
    id = TL.readVector(buffer, true, new tl.TInputUser[0]);
  }
  
  public GetUsers(tl.TInputUser[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd91a548);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(id);
  }
  
  public String toString() {
    return "(GetUsers id:" + TL.toString(id) + ")";
  }
}
