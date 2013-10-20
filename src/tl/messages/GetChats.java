package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetChats extends tl.TLFunction {
  public int[] id;
  
  public GetChats(ByteBuffer buffer) {
    id = TL.readVectorInt(buffer, true);
  }
  
  public GetChats(int[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3c6aa187);
    }
    TL.writeVector(buffer, id, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(GetChats id:" + TL.toString(id) + ")";
  }
}
