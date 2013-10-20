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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x395f9d7e);
    }
    TL.writeVector(buffer, id, true, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at RestoreMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + id.length * 4;
  }
  
  public String toString() {
    return "(messages.restoreMessages id:" + TL.toString(id) + ")";
  }
}
