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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7fcb13a8);
    }
    photo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionChatEditPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + photo.length();
  }
  
  public String toString() {
    return "(messageActionChatEditPhoto photo:" + photo + ")";
  }
}
