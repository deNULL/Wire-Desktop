package tl;

import java.nio.ByteBuffer;

public class EncryptedChatDiscarded extends tl.TEncryptedChat {

  
  public EncryptedChatDiscarded(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
  }
  
  public EncryptedChatDiscarded(int id) {
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x13d6dd27);
    }
    buffer.putInt(id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedChatDiscarded: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(encryptedChatDiscarded id:" + id + ")";
  }
}
