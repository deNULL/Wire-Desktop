package tl;

import java.nio.ByteBuffer;

public class EncryptedChatEmpty extends tl.TEncryptedChat {
  
  public EncryptedChatEmpty(ByteBuffer buffer) {
    id = buffer.getInt();
  }
  
  public EncryptedChatEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xab7ec0a0);
    }
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(EncryptedChatEmpty id:" + id + ")";
  }
}
