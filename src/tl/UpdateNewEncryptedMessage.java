package tl;

import java.nio.ByteBuffer;

public class UpdateNewEncryptedMessage extends tl.TUpdate {
  public tl.TEncryptedMessage message;
  public int qts;
  
  public UpdateNewEncryptedMessage(ByteBuffer buffer) {
    message = (tl.TEncryptedMessage) TL.read(buffer);
    qts = buffer.getInt();
  }
  
  public UpdateNewEncryptedMessage(tl.TEncryptedMessage message, int qts) {
    this.message = message;
    this.qts = qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x12bcbd9a);
    }
    message.writeTo(buffer, false);
    buffer.putInt(qts);
  	return buffer;
  }
  
  public int length() {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(UpdateNewEncryptedMessage message:" + message + " qts:" + qts + ")";
  }
}
