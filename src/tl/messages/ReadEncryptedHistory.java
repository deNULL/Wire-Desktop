package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReadEncryptedHistory extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public int max_date;
  
  public ReadEncryptedHistory(ByteBuffer buffer) {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    max_date = buffer.getInt();
  }
  
  public ReadEncryptedHistory(tl.TInputEncryptedChat peer, int max_date) {
    this.peer = peer;
    this.max_date = max_date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7f4b690a);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(max_date);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(ReadEncryptedHistory peer:" + peer + " max_date:" + max_date + ")";
  }
}
