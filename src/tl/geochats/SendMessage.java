package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class SendMessage extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public String message;
  public long random_id;
  
  public SendMessage(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    message = new String(TL.readString(buffer), "UTF8");
    random_id = buffer.getLong();
  }
  
  public SendMessage(tl.TInputGeoChat peer, String message, long random_id) {
    this.peer = peer;
    this.message = message;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x61b0044);
    }
    peer.writeTo(buffer, true);
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    buffer.putLong(random_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + peer.length() + TL.length(message.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(geochats.sendMessage peer:" + peer + " message:" + "message" + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
