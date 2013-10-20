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
    if (boxed) {
      buffer.putInt(0xca30a5b1);
    }
    id.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(GetFullUser id:" + id + ")";
  }
}
