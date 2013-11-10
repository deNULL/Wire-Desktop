package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileWebp extends tl.storage.TFileType {

  
  public FileWebp(ByteBuffer buffer) throws Exception {

  }
  
  public FileWebp() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1081464c);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileWebp: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(storage.fileWebp)";
  }
}
