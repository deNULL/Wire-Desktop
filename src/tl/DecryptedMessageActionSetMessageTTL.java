package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageActionSetMessageTTL extends tl.TDecryptedMessageAction {

  
  public DecryptedMessageActionSetMessageTTL(ByteBuffer buffer) {
    ttl_seconds = buffer.getInt();
  }
  
  public DecryptedMessageActionSetMessageTTL(int ttl_seconds) {
    this.ttl_seconds = ttl_seconds;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa1733aec);
    }
    buffer.putInt(ttl_seconds);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageActionSetMessageTTL: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(decryptedMessageActionSetMessageTTL ttl_seconds:" + ttl_seconds + ")";
  }
}
