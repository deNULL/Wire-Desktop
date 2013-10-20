package tl.storage;

import tl.TL;
import java.nio.ByteBuffer;

public class FilePartial extends tl.storage.TFileType {

  
  public FilePartial(ByteBuffer buffer) {

  }
  
  public FilePartial() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x40bc6f52);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(FilePartial)";
  }
}
