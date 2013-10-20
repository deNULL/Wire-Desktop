package tl;

import java.nio.ByteBuffer;

public class UpdateNewGeoChatMessage extends tl.TUpdate {
  public tl.TGeoChatMessage message;
  
  public UpdateNewGeoChatMessage(ByteBuffer buffer) {
    message = (tl.TGeoChatMessage) TL.read(buffer);
  }
  
  public UpdateNewGeoChatMessage(tl.TGeoChatMessage message) {
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5a68e3f7);
    }
    message.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + message.length();
  }
  
  public String toString() {
    return "(UpdateNewGeoChatMessage message:" + message + ")";
  }
}
