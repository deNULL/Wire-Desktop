package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class UpdateStatus extends tl.TLFunction {
  public boolean offline;
  
  public UpdateStatus(ByteBuffer buffer) {
    offline = (buffer.getInt() == 0x997275b5);
  }
  
  public UpdateStatus(boolean offline) {
    this.offline = offline;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6628562c);
    }
    buffer.putInt(offline ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateStatus: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(account.updateStatus offline:" + (offline ? "true" : "false") + ")";
  }
}
