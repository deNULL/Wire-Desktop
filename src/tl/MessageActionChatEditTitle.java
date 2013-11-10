package tl;

import java.nio.ByteBuffer;

public class MessageActionChatEditTitle extends tl.TMessageAction {

  
  public MessageActionChatEditTitle(ByteBuffer buffer) throws Exception {
    title = new String(TL.readString(buffer), "UTF8");
  }
  
  public MessageActionChatEditTitle(String title) {
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb5a1ce5a);
    }
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatEditTitle: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(title.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(messageActionChatEditTitle title:" + "title" + ")";
  }
}
