package tl;

import java.nio.ByteBuffer;

public class Dialog extends tl.TDialog {
  
  public Dialog(ByteBuffer buffer) {
    peer = (tl.TPeer) TL.read(buffer);
    top_message = buffer.getInt();
    unread_count = buffer.getInt();
  }
  
  public Dialog(tl.TPeer peer, int top_message, int unread_count) {
    this.peer = peer;
    this.top_message = top_message;
    this.unread_count = unread_count;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x214a8cdf);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(top_message);
    buffer.putInt(unread_count);
  	return buffer;
  }
  
  public int length() {
    return 12 + peer.length();
  }
  
  public String toString() {
    return "(Dialog peer:" + peer + " top_message:" + top_message + " unread_count:" + unread_count + ")";
  }
}
