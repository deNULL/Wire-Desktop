package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileUnknown extends tl.storage.TFileType {

  
  public FileUnknown(ByteBuffer buffer) {

  }
  
  public FileUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xaa963b05);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileUnknown: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(storage.fileUnknown)";
  }
}
