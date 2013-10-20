package tl;

import java.nio.ByteBuffer;

public class UpdateNewMessage extends tl.TUpdate {
  public tl.TMessage message;
  
  public UpdateNewMessage(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
    pts = buffer.getInt();
  }
  
  public UpdateNewMessage(tl.TMessage message, int pts) {
    this.message = message;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x13abdb3);
    }
    message.writeTo(buffer, true);
    buffer.putInt(pts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNewMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(updateNewMessage message:" + message + " pts:" + pts + ")";
  }
}
