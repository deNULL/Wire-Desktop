package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMov extends tl.storage.TFileType {

  
  public FileMov(ByteBuffer buffer) {

  }
  
  public FileMov() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4b09ebbc);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileMov: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(storage.fileMov)";
  }
}
