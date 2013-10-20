package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileMov extends tl.storage.TFileType {

  
  public FileMov(ByteBuffer buffer) {

  }
  
  public FileMov() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4b09ebbc);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileMov)";
  }
}
