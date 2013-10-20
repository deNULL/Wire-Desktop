package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaEmpty extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaEmpty(ByteBuffer buffer) {

  }
  
  public DecryptedMessageMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x89f5c4a);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageMediaEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(decryptedMessageMediaEmpty)";
  }
}
