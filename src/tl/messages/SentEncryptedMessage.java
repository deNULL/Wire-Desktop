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
    if (boxed) {
      buffer.putInt(0x560f8935);
    }
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(SentEncryptedMessage date:" + date + ")";
  }
}
