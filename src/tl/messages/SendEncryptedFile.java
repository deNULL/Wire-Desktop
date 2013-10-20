package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendEncryptedFile extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public long random_id;
  public byte[] data;
  public tl.TInputEncryptedFile file;
  
  public SendEncryptedFile(ByteBuffer buffer) {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    random_id = buffer.getLong();
    data = TL.readString(buffer);
    file = (tl.TInputEncryptedFile) TL.read(buffer);
  }
  
  public SendEncryptedFile(tl.TInputEncryptedChat peer, long random_id, byte[] data, tl.TInputEncryptedFile file) {
    this.peer = peer;
    this.random_id = random_id;
    this.data = data;
    this.file = file;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x9a901b66);
    }
    peer.writeTo(buffer, false);
    buffer.putLong(random_id);
    TL.writeString(buffer, data, false);
    file.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + peer.length() + TL.length(data) + file.length();
  }
  
  public String toString() {
    return "(SendEncryptedFile peer:" + peer + " random_id:" + String.format("0x%016x", random_id) + " data:" + TL.toString(data) + " file:" + file + ")";
  }
}
