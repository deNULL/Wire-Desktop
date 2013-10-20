package tl;

import java.nio.ByteBuffer;

public class InputGeoChat extends tl.TInputGeoChat {
  
  public InputGeoChat(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    access_hash = buffer.getLong();
  }
  
  public InputGeoChat(int chat_id, long access_hash) {
    this.chat_id = chat_id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x74d456fa);
    }
    buffer.putInt(chat_id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(InputGeoChat chat_id:" + chat_id + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
