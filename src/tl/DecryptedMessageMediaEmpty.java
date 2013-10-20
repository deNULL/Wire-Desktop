package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageMediaEmpty extends tl.TDecryptedMessageMedia {

  
  public DecryptedMessageMediaEmpty(ByteBuffer buffer) {

  }
  
  public DecryptedMessageMediaEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x89f5c4a);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(DecryptedMessageMediaEmpty)";
  }
}
