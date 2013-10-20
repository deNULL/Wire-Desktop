package tl;

import java.nio.ByteBuffer;

public class UpdateNewEncryptedMessage extends tl.TUpdate {
  public tl.TMessage message;
  
  public UpdateNewEncryptedMessage(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    qts = buffer.getInt();
  }
  
  public UpdateNewEncryptedMessage(tl.TMessage message, int qts) {
    this.message = message;
    this.qts = qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
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
  
  public int length() {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(updateNewEncryptedMessage message:" + message + " qts:" + qts + ")";
  }
}
