package tl;

import java.nio.ByteBuffer;

public class ChatLocated extends tl.TChatLocated {
  
  public ChatLocated(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    distance = buffer.getInt();
  }
  
  public ChatLocated(int chat_id, int distance) {
    this.chat_id = chat_id;
    this.distance = distance;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3631cf4c);
    }
    buffer.putInt(chat_id);
    buffer.putInt(distance);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(ChatLocated chat_id:" + chat_id + " distance:" + distance + ")";
  }
}
