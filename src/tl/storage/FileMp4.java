package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMp4 extends tl.storage.TFileType {

  
  public FileMp4(ByteBuffer buffer) throws Exception {

  }
  
  public FileMp4() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb3cea0e4);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileMp4: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(storage.fileMp4)";
  }
}
