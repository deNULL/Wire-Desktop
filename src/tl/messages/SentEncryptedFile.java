package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SentEncryptedFile extends tl.messages.TSentEncryptedMessage {
  
  public SentEncryptedFile(ByteBuffer buffer) {
    date = buffer.getInt();
    file = (tl.TEncryptedFile) TL.read(buffer);
  }
  
  public SentEncryptedFile(int date, tl.TEncryptedFile file) {
    this.date = date;
    this.file = file;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9493ff32);
    }
    buffer.putInt(date);
    file.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + file.length();
  }
  
  public String toString() {
    return "(SentEncryptedFile date:" + date + " file:" + file + ")";
  }
}
