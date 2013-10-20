package tl;

import java.nio.ByteBuffer;

public class GeoChatMessageEmpty extends tl.TGeoChatMessage {
  
  public GeoChatMessageEmpty(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    id = buffer.getInt();
  }
  
  public GeoChatMessageEmpty(int chat_id, int id) {
    this.chat_id = chat_id;
    this.id = id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x60311a9b);
    }
    buffer.putInt(chat_id);
    buffer.putInt(id);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(GeoChatMessageEmpty chat_id:" + chat_id + " id:" + id + ")";
  }
}
