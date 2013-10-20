package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SentEncryptedMessage extends tl.messages.TSentEncryptedMessage {

  
  public SentEncryptedMessage(ByteBuffer buffer) {
    date = buffer.getInt();
  }
  
  public SentEncryptedMessage(int date) {
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x560f8935);
    }
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SentEncryptedMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(messages.sentEncryptedMessage date:" + date + ")";
  }
}
