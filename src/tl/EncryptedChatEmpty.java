package tl;

import java.nio.ByteBuffer;

public class EncryptedChatEmpty extends tl.TEncryptedChat {

  
  public EncryptedChatEmpty(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
  }
  
  public EncryptedChatEmpty(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xab7ec0a0);
    }
    buffer.putInt(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedChatEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(encryptedChatEmpty id:" + id + ")";
  }
}
