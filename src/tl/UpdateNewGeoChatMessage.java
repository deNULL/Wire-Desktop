package tl;

import java.nio.ByteBuffer;

public class UpdateNewGeoChatMessage extends tl.TUpdate {
  public tl.TMessage message;
  
  public UpdateNewGeoChatMessage(ByteBuffer buffer) {
    message = (tl.TMessage) TL.read(buffer);
  }
  
  public UpdateNewGeoChatMessage(tl.TMessage message) {
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5a68e3f7);
    }
    message.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateNewGeoChatMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + message.length();
  }
  
  public String toString() {
    return "(updateNewGeoChatMessage message:" + message + ")";
  }
}
