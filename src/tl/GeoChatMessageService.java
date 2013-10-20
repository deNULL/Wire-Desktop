package tl;

import java.nio.ByteBuffer;

public class GeoChatMessageService extends tl.TGeoChatMessage {
  
  public GeoChatMessageService(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    id = buffer.getInt();
    from_id = buffer.getInt();
    date = buffer.getInt();
    action = (tl.TMessageAction) TL.read(buffer);
  }
  
  public GeoChatMessageService(int chat_id, int id, int from_id, int date, tl.TMessageAction action) {
    this.chat_id = chat_id;
    this.id = id;
    this.from_id = from_id;
    this.date = date;
    this.action = action;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd34fa24e);
    }
    buffer.putInt(chat_id);
    buffer.putInt(id);
    buffer.putInt(from_id);
    buffer.putInt(date);
    action.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 20 + action.length();
  }
  
  public String toString() {
    return "(GeoChatMessageService chat_id:" + chat_id + " id:" + id + " from_id:" + from_id + " date:" + date + " action:" + action + ")";
  }
}
