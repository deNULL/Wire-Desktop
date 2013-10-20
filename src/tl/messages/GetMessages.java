package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetMessages extends tl.TLFunction {
  public int[] id;
  
  public GetMessages(ByteBuffer buffer) {
    id = TL.readVectorInt(buffer, true);
  }
  
  public GetMessages(int[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4222fa74);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(GetMessages id:" + TL.toString(id) + ")";
  }
}
