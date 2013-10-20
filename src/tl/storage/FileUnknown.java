package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileUnknown extends tl.storage.TFileType {

  
  public FileUnknown(ByteBuffer buffer) {

  }
  
  public FileUnknown() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xaa963b05);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileUnknown)";
  }
}
