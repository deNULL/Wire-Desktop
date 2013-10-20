package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMp3 extends tl.storage.TFileType {

  
  public FileMp3(ByteBuffer buffer) {

  }
  
  public FileMp3() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x528a0677);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileMp3)";
  }
}
