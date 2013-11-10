package tl;

import java.nio.ByteBuffer;

public class EncryptedMessage extends tl.TEncryptedMessage {

  
  public EncryptedMessage(ByteBuffer buffer) throws Exception {
    random_id = buffer.getLong();
    chat_id = buffer.getInt();
    date = buffer.getInt();
    bytes = TL.readString(buffer);
    file = (tl.TEncryptedFile) TL.read(buffer);
  }
  
  public EncryptedMessage(long random_id, int chat_id, int date, byte[] bytes, tl.TEncryptedFile file) {
    this.random_id = random_id;
    this.chat_id = chat_id;
    this.date = date;
    this.bytes = bytes;
    this.file = file;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xed18c118);
    }
    buffer.putLong(random_id);
    buffer.putInt(chat_id);
    buffer.putInt(date);
    TL.writeString(buffer, bytes, false);
    file.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + TL.length(bytes) + file.length();
  }
  
  public String toString() {
    return "(encryptedMessage random_id:" + String.format("0x%016x", random_id) + " chat_id:" + chat_id + " date:" + date + " bytes:" + TL.toString(bytes) + " file:" + file + ")";
  }
}
