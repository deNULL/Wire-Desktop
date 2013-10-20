package tl;

import java.nio.ByteBuffer;

public class UpdateNewMessage extends tl.TUpdate {
  public tl.TMessage message;
  public int pts;
  
  public UpdateNewMessage(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    pts = buffer.getInt();
  }
  
  public UpdateNewMessage(tl.TMessage message, int pts) {
    this.message = message;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x13abdb3);
    }
    message.writeTo(buffer, false);
    buffer.putInt(pts);
  	return buffer;
  }
  
  public int length() {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(UpdateNewMessage message:" + message + " pts:" + pts + ")";
  }
}
