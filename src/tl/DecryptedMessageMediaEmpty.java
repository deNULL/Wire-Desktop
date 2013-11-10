package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaEmpty extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public DecryptedMessageMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x89f5c4a);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageMediaEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(decryptedMessageMediaEmpty)";
  }
}
