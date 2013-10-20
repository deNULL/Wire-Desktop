package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FilePartial extends tl.storage.TFileType {

  
  public FilePartial(ByteBuffer buffer) {

  }
  
  public FilePartial() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x40bc6f52);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FilePartial: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(storage.filePartial)";
  }
}
