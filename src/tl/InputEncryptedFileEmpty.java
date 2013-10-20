package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileEmpty extends tl.TInputEncryptedFile {

  
  public InputEncryptedFileEmpty(ByteBuffer buffer) {

  }
  
  public InputEncryptedFileEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1837c364);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(InputEncryptedFileEmpty)";
  }
}
