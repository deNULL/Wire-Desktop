package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class DeleteMessages extends tl.TLFunction {
  public int[] id;
  
  public DeleteMessages(ByteBuffer buffer) {
    id = TL.readVectorInt(buffer, true);
  }
  
  public DeleteMessages(int[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x14f2dd0a);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(DeleteMessages id:" + TL.toString(id) + ")";
  }
}
