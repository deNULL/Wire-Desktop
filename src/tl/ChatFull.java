package tl;

import java.nio.ByteBuffer;

public class ChatFull extends tl.TChatFull {

  
  public ChatFull(ByteBuffer buffer) {
    id = buffer.getInt();
    participants = (tl.TChatParticipants) TL.read(buffer);
    chat_photo = (tl.TPhoto) TL.read(buffer);
    notify_settings = (tl.TPeerNotifySettings) TL.read(buffer);
  }
  
  public ChatFull(int id, tl.TChatParticipants participants, tl.TPhoto chat_photo, tl.TPeerNotifySettings notify_settings) {
    this.id = id;
    this.participants = participants;
    this.chat_photo = chat_photo;
    this.notify_settings = notify_settings;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x630e61be);
    }
    buffer.putInt(id);
    participants.writeTo(buffer, true);
    chat_photo.writeTo(buffer, true);
    notify_settings.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatFull: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + participants.length() + chat_photo.length() + notify_settings.length();
  }
  
  public String toString() {
    return "(chatFull id:" + id + " participants:" + participants + " chat_photo:" + chat_photo + " notify_settings:" + notify_settings + ")";
  }
}
