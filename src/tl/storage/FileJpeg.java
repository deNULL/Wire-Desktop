package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileJpeg extends tl.storage.TFileType {

  
  public FileJpeg(ByteBuffer buffer) {

  }
  
  public FileJpeg() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7efe0e);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileJpeg)";
  }
}
