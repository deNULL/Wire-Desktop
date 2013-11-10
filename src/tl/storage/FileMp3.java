package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMp3 extends tl.storage.TFileType {

  
  public FileMp3(ByteBuffer buffer) throws Exception {

  }
  
  public FileMp3() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x528a0677);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileMp3: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(storage.fileMp3)";
  }
}
