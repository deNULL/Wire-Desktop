package tl;

import java.nio.ByteBuffer;

public class MessageActionChatEditPhoto extends tl.TMessageAction {
  
  public MessageActionChatEditPhoto(ByteBuffer buffer) {
    photo = (tl.TPhoto) TL.read(buffer);
  }
  
  public MessageActionChatEditPhoto(tl.TPhoto photo) {
    this.photo = photo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x7fcb13a8);
    }
    photo.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + photo.length();
  }
  
  public String toString() {
    return "(MessageActionChatEditPhoto photo:" + photo + ")";
  }
}
