package tl;

import java.nio.ByteBuffer;

public class EncryptedFileEmpty extends tl.TEncryptedFile {

  
  public EncryptedFileEmpty(ByteBuffer buffer) {

  }
  
  public EncryptedFileEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xc21f497e);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(EncryptedFileEmpty)";
  }
}
