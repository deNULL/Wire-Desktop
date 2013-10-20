package tl;

import java.nio.ByteBuffer;

public class MessageActionChatEditTitle extends tl.TMessageAction {
  
  public MessageActionChatEditTitle(ByteBuffer buffer) {
    title = new String(TL.readString(buffer));
  }
  
  public MessageActionChatEditTitle(String title) {
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb5a1ce5a);
    }
    TL.writeString(buffer, title.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(MessageActionChatEditTitle title:" + "title" + ")";
  }
}
