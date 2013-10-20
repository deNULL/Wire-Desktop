package tl;

import java.nio.ByteBuffer;

public class UpdateEncryption extends tl.TUpdate {
  
  public UpdateEncryption(ByteBuffer buffer) {
    chat = (tl.TEncryptedChat) TL.read(buffer);
    date = buffer.getInt();
  }
  
  public UpdateEncryption(tl.TEncryptedChat chat, int date) {
    this.chat = chat;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb4a2e88d);
    }
    chat.writeTo(buffer, false);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 8 + chat.length();
  }
  
  public String toString() {
    return "(UpdateEncryption chat:" + chat + " date:" + date + ")";
  }
}
