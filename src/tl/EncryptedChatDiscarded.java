package tl;

import java.nio.ByteBuffer;

public class EncryptedChatDiscarded extends tl.TEncryptedChat {
  
  public EncryptedChatDiscarded(ByteBuffer buffer) {
    id = buffer.getInt();
  }
  
  public EncryptedChatDiscarded(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x13d6dd27);
    }
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(EncryptedChatDiscarded id:" + id + ")";
  }
}
