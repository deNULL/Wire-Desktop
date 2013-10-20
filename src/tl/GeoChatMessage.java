package tl;

import java.nio.ByteBuffer;

public class GeoChatMessage extends tl.TGeoChatMessage {
  
  public GeoChatMessage(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    id = buffer.getInt();
    from_id = buffer.getInt();
    date = buffer.getInt();
    message = new String(TL.readString(buffer));
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
    if (boxed) {
      buffer.putInt(0x4505f8e1);
    }
    buffer.putInt(chat_id);
    buffer.putInt(id);
    buffer.putInt(from_id);
    buffer.putInt(date);
    TL.writeString(buffer, message.getBytes(), false);
    media.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 20 + TL.length(message.getBytes()) + media.length();
  }
  
  public String toString() {
    return "(GeoChatMessage chat_id:" + chat_id + " id:" + id + " from_id:" + from_id + " date:" + date + " message:" + "message" + " media:" + media + ")";
  }
}
