package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FileGif extends tl.storage.TFileType {

  
  public FileGif(ByteBuffer buffer) {

  }
  
  public FileGif() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xcae1aadf);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FileGif)";
  }
}
