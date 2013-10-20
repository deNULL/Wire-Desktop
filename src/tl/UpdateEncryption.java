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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb4a2e88d);
    }
    chat.writeTo(buffer, true);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateEncryption: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + chat.length();
  }
  
  public String toString() {
    return "(updateEncryption chat:" + chat + " date:" + date + ")";
  }
}
