package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendEncryptedService extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public long random_id;
  public byte[] data;
  
  public SendEncryptedService(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    random_id = buffer.getLong();
    data = TL.readString(buffer);
  }
  
  public SendEncryptedService(tl.TInputEncryptedChat peer, long random_id, byte[] data) {
    this.peer = peer;
    this.random_id = random_id;
    this.data = data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x32d439a4);
    }
    peer.writeTo(buffer, true);
    buffer.putLong(random_id);
    TL.writeString(buffer, data, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendEncryptedService: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + peer.length() + TL.length(data);
  }
  
  public String toString() {
    return "(messages.sendEncryptedService peer:" + peer + " random_id:" + String.format("0x%016x", random_id) + " data:" + TL.toString(data) + ")";
  }
}
