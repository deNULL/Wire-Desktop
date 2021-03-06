package tl;

import java.nio.ByteBuffer;

public class InputEncryptedChat extends tl.TInputEncryptedChat {

  
  public InputEncryptedChat(ByteBuffer buffer) throws Exception {
    chat_id = buffer.getInt();
    access_hash = buffer.getLong();
  }
  
  public InputEncryptedChat(int chat_id, long access_hash) {
    this.chat_id = chat_id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf141b5e1);
    }
    buffer.putInt(chat_id);
    buffer.putLong(access_hash);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputEncryptedChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12;
  }
  
  public String toString() {
    return "(inputEncryptedChat chat_id:" + chat_id + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
