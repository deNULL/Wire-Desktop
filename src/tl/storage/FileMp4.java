package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMp4 extends tl.storage.TFileType {

  
  public FileMp4(ByteBuffer buffer) {

  }
  
  public FileMp4() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb3cea0e4);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileMp4)";
  }
}
