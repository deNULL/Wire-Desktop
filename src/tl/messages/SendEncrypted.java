package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendEncrypted extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public long random_id;
  public byte[] data;
  
  public SendEncrypted(ByteBuffer buffer) {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    random_id = buffer.getLong();
    data = TL.readString(buffer);
  }
  
  public SendEncrypted(tl.TInputEncryptedChat peer, long random_id, byte[] data) {
    this.peer = peer;
    this.random_id = random_id;
    this.data = data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa9776773);
    }
    peer.writeTo(buffer, true);
    buffer.putLong(random_id);
    TL.writeString(buffer, data, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendEncrypted: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length() + TL.length(data);
  }
  
  public String toString() {
    return "(messages.sendEncrypted peer:" + peer + " random_id:" + String.format("0x%016x", random_id) + " data:" + TL.toString(data) + ")";
  }
}
