package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class ReadEncryptedHistory extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public int max_date;
  
  public ReadEncryptedHistory(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    max_date = buffer.getInt();
  }
  
  public ReadEncryptedHistory(tl.TInputEncryptedChat peer, int max_date) {
    this.peer = peer;
    this.max_date = max_date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7f4b690a);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(max_date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ReadEncryptedHistory: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(messages.readEncryptedHistory peer:" + peer + " max_date:" + max_date + ")";
  }
}
