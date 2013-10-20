package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Block extends tl.TLFunction {
  public tl.TInputUser id;
  
  public Block(ByteBuffer buffer) {
    id = (tl.TInputUser) TL.read(buffer);
  }
  
  public Block(tl.TInputUser id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x332b49fc);
    }
    id.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Block: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + id.length();
  }
  
  public String toString() {
    return "(contacts.block id:" + id + ")";
  }
}
