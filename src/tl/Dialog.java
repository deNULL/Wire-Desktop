package tl;

import java.nio.ByteBuffer;

public class Dialog extends tl.TDialog {

  
  public Dialog(ByteBuffer buffer) throws Exception {
    peer = (tl.TPeer) TL.read(buffer);
    top_message = buffer.getInt();
    unread_count = buffer.getInt();
  }
  
  public Dialog(tl.TPeer peer, int top_message, int unread_count) {
    this.peer = peer;
    this.top_message = top_message;
    this.unread_count = unread_count;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x214a8cdf);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(top_message);
    buffer.putInt(unread_count);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Dialog: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + peer.length();
  }
  
  public String toString() {
    return "(dialog peer:" + peer + " top_message:" + top_message + " unread_count:" + unread_count + ")";
  }
}
