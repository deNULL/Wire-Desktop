package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatPhoto extends tl.TLFunction {
  public int chat_id;
  public tl.TInputChatPhoto photo;
  
  public EditChatPhoto(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    photo = (tl.TInputChatPhoto) TL.read(buffer);
  }
  
  public EditChatPhoto(int chat_id, tl.TInputChatPhoto photo) {
    this.chat_id = chat_id;
    this.photo = photo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd881821d);
    }
    buffer.putInt(chat_id);
    photo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EditChatPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + photo.length();
  }
  
  public String toString() {
    return "(messages.editChatPhoto chat_id:" + chat_id + " photo:" + photo + ")";
  }
}
