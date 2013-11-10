package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetChats extends tl.TLFunction {
  public int[] id;
  
  public GetChats(ByteBuffer buffer) throws Exception {
    id = TL.readVectorInt(buffer, true);
  }
  
  public GetChats(int[] id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3c6aa187);
    }
    TL.writeVector(buffer, id, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetChats: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(messages.getChats id:" + TL.toString(id) + ")";
  }
}
