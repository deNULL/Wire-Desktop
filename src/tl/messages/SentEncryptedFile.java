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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9493ff32);
    }
    buffer.putInt(date);
    file.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SentEncryptedFile: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + file.length();
  }
  
  public String toString() {
    return "(messages.sentEncryptedFile date:" + date + " file:" + file + ")";
  }
}
