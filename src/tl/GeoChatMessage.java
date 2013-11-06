package tl;

import java.nio.ByteBuffer;

public class GeoChatMessage extends tl.TGeoChatMessage {

  
  public GeoChatMessage(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    id = buffer.getInt();
    from_id = buffer.getInt();
    date = buffer.getInt();
    try {  message = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    media = (tl.TMessageMedia) TL.read(buffer);
  }
  
  public GeoChatMessage(int chat_id, int id, int from_id, int date, String message, tl.TMessageMedia media) {
    this.chat_id = chat_id;
    this.id = id;
    this.from_id = from_id;
    this.date = date;
    this.message = message;
    this.media = media;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4505f8e1);
    }
    buffer.putInt(chat_id);
    buffer.putInt(id);
    buffer.putInt(from_id);
    buffer.putInt(date);
    try { TL.writeString(buffer, message.getBytes("UTF8"), false); } catch (Exception e) { };
    media.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GeoChatMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(message.getBytes()) + media.length();
  }
  
  public String toString() {
    return "(geoChatMessage chat_id:" + chat_id + " id:" + id + " from_id:" + from_id + " date:" + date + " message:" + "message" + " media:" + media + ")";
  }
}
