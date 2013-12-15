package tl;

import java.nio.ByteBuffer;

public class UpdateNewEncryptedMessage extends tl.TUpdate {
  public tl.TEncryptedMessage message;
  
  public UpdateNewEncryptedMessage(ByteBuffer buffer) throws Exception {
    message = (tl.TEncryptedMessage) TL.read(buffer);
    qts = buffer.getInt();
  }
  
  public UpdateNewEncryptedMessage(tl.TEncryptedMessage message, int qts) {
    this.message = message;
    this.qts = qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x12bcbd9a);
    }
    message.writeTo(buffer, true);
    buffer.putInt(qts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNewEncryptedMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(updateNewEncryptedMessage message:" + message + " qts:" + qts + ")";
  }
}
