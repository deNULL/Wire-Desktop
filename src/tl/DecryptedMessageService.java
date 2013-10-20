package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageService extends tl.TDecryptedMessage {

  
  public DecryptedMessageService(ByteBuffer buffer) {
    random_id = buffer.getLong();
    random_bytes = TL.readString(buffer);
    action = (tl.TDecryptedMessageAction) TL.read(buffer);
  }
  
  public DecryptedMessageService(long random_id, byte[] random_bytes, tl.TDecryptedMessageAction action) {
    this.random_id = random_id;
    this.random_bytes = random_bytes;
    this.action = action;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xaa48327d);
    }
    buffer.putLong(random_id);
    TL.writeString(buffer, random_bytes, false);
    action.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageService: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(random_bytes) + action.length();
  }
  
  public String toString() {
    return "(decryptedMessageService random_id:" + String.format("0x%016x", random_id) + " random_bytes:" + TL.toString(random_bytes) + " action:" + action + ")";
  }
}
