package tl;

import java.nio.ByteBuffer;

public class MessageActionChatEditTitle extends tl.TMessageAction {

  
  public MessageActionChatEditTitle(ByteBuffer buffer) {
    try {  title = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public MessageActionChatEditTitle(String title) {
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb5a1ce5a);
    }
    try { TL.writeString(buffer, title.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatEditTitle: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(messageActionChatEditTitle title:" + "title" + ")";
  }
}
