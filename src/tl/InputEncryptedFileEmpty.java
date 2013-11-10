package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileEmpty extends tl.TInputEncryptedFile {

  
  public InputEncryptedFileEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public InputEncryptedFileEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x1837c364);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputEncryptedFileEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(inputEncryptedFileEmpty)";
  }
}
