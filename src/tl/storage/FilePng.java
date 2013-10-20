package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FilePng extends tl.storage.TFileType {

  
  public FilePng(ByteBuffer buffer) {

  }
  
  public FilePng() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa4f63c0);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FilePng)";
  }
}
