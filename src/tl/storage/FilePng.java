package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FilePng extends tl.storage.TFileType {

  
  public FilePng(ByteBuffer buffer) throws Exception {

  }
  
  public FilePng() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa4f63c0);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FilePng: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(storage.filePng)";
  }
}
