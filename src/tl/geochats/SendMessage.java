package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class SendMessage extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public String message;
  public long random_id;
  
  public SendMessage(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    message = new String(TL.readString(buffer));
    random_id = buffer.getLong();
  }
  
  public SendMessage(tl.TInputGeoChat peer, String message, long random_id) {
    this.peer = peer;
    this.message = message;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x61b0044);
    }
    peer.writeTo(buffer, false);
    TL.writeString(buffer, message.getBytes(), false);
    buffer.putLong(random_id);
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length() + TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(SendMessage peer:" + peer + " message:" + "message" + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
