package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class RestoreMessages extends tl.TLFunction {
  public int[] id;
  
  public RestoreMessages(ByteBuffer buffer) {
    id = TL.readVectorInt(buffer, true);
  }
  
  public RestoreMessages(int[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x395f9d7e);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(RestoreMessages id:" + TL.toString(id) + ")";
  }
}
