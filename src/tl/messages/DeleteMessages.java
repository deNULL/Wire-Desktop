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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x14f2dd0a);
    }
    TL.writeVector(buffer, id, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DeleteMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(messages.deleteMessages id:" + TL.toString(id) + ")";
  }
}
