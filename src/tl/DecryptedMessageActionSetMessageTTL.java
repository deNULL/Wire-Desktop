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
    if (boxed) {
      buffer.putInt(0xa1733aec);
    }
    buffer.putInt(ttl_seconds);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(DecryptedMessageActionSetMessageTTL ttl_seconds:" + ttl_seconds + ")";
  }
}
