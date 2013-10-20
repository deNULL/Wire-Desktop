package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileWebp extends tl.storage.TFileType {

  
  public FileWebp(ByteBuffer buffer) {

  }
  
  public FileWebp() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1081464c);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileWebp)";
  }
}
