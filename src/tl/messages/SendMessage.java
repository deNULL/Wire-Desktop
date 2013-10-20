package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendMessage extends tl.TLFunction {
  public tl.TInputPeer peer;
  public String message;
  public long random_id;
  
  public SendMessage(ByteBuffer buffer) {
    peer = (tl.TInputPeer) TL.read(buffer);
    message = new String(TL.readString(buffer));
    random_id = buffer.getLong();
  }
  
  public SendMessage(tl.TInputPeer peer, String message, long random_id) {
    this.peer = peer;
    this.message = message;
    this.random_id = random_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4cde0aab);
    }
    peer.writeTo(buffer, true);
    TL.writeString(buffer, message.getBytes(), false);
    buffer.putLong(random_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length() + TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(messages.sendMessage peer:" + peer + " message:" + "message" + " random_id:" + String.format("0x%016x", random_id) + ")";
  }
}
